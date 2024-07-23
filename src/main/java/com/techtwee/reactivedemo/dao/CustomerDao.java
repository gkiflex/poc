package com.techtwee.reactivedemo.dao;

import com.techtwee.reactivedemo.model.Customer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class CustomerDao {

    public List<Customer> getCustomers(){

            return IntStream.rangeClosed(0, 15)
                    .peek(i -> System.out.println("Processing " + i))
                    .peek(CustomerDao::sleepExecution)
                    .mapToObj(i-> new Customer(String.valueOf(i), "cust-"+i))
                    .toList();
    }

    public Flux<Customer> getCustomerStream(){
        return Flux.range(0, 15)
                .doOnNext(i -> System.out.println("Processing " + i))
                .delayElements(Duration.ofSeconds(1))
                .map(i -> new Customer(String.valueOf(i), "cust-"+i));
    }

    public static void sleepExecution(int i){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
