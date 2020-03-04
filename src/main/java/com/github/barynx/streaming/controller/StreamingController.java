package com.github.barynx.streaming.controller;

import com.github.barynx.streaming.PrimeNumberRequest;
import com.github.barynx.streaming.PrimeNumberResponse;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import com.github.barynx.streaming.PrimeNumberGenerationServiceGrpc;
import com.github.barynx.streaming.PrimeNumberGenerationServiceGrpc.PrimeNumberGenerationServiceBlockingStub;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Iterator;

@RestController
public class StreamingController {

    @GetMapping(value = "/prime/{number}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> getPrimeNumbersStream(@PathVariable("number") Long number){

        Channel channel = ManagedChannelBuilder.forAddress("localhost", 8081).usePlaintext().build();
        PrimeNumberGenerationServiceBlockingStub client = PrimeNumberGenerationServiceGrpc.newBlockingStub(channel);
        PrimeNumberRequest request = PrimeNumberRequest.newBuilder().setLimit(number.intValue()).build();
        Iterator<PrimeNumberResponse> responseStream = client.generate(request);

        return Flux.generate(
                () -> 0,
                (state, sink) -> {
                    if (responseStream.hasNext()){
                        long nextPrimeNumber = responseStream.next().getPrime();
                        sink.next(nextPrimeNumber);
                    } else sink.complete();
                    return state;
                });
    }
}
