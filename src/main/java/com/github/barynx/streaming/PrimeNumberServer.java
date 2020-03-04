package com.github.barynx.streaming;

import com.github.barynx.streaming.services.PrimeNumberGenerationServiceImp;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class PrimeNumberServer {

    public static void main(String[] arg) {
        try {
            Server server = ServerBuilder.forPort(8081)
                    .addService(new PrimeNumberGenerationServiceImp())
                    .build();
            System.out.println("Starting Server......");
            server.start();
            System.out.println("Server has started ========");

            server.awaitTermination();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
