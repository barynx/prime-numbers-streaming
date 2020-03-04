package com.github.barynx.streaming.services;


import com.github.barynx.streaming.PrimeNumberGenerationServiceGrpc;
import com.github.barynx.streaming.PrimeNumberRequest;
import com.github.barynx.streaming.PrimeNumberResponse;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;

public class PrimeNumberGenerationServiceImp extends PrimeNumberGenerationServiceGrpc.PrimeNumberGenerationServiceImplBase {

    @Override
    public void generate(PrimeNumberRequest request, StreamObserver<PrimeNumberResponse> responseObserver) {

        // Sieve of Eratosthenes algorithm implementation for generating prime numbers up to a given number

        int n = request.getLimit();

        boolean prime[] = new boolean[n + 1];
        Arrays.fill(prime, true);
        for (int p = 2; p * p <= n; p++) {
            if (prime[p]) {
                for (int i = p * 2; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }

        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                // intentional pause to demonstrate that ProxyService is reading from the stream until the end of generation
                try { Thread.sleep(1000); } catch (Exception e){}
                responseObserver
                        .onNext(PrimeNumberResponse
                                .newBuilder()
                                .setPrime(i)
                                .build());
            }
        }
        responseObserver.onCompleted();
    }
}