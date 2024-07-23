package com.techtwee.reactivedemo.handlers;

import com.techtwee.reactivedemo.dao.CustomerDao;
import com.techtwee.reactivedemo.model.Customer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    private final CustomerDao customerDao;
    public CustomerHandler(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Mono<ServerResponse> getAllCustomerStream(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customerDao.getCustomerStream(), Customer.class);
    }

    public Mono<ServerResponse> getCustomerStream(ServerRequest serverRequest){
        String custId = serverRequest.pathVariable("custId");
        Mono<Customer> filterCustomerMono = customerDao.getCustomerStream().filter(customer -> customer.custId().equalsIgnoreCase(custId)).next();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(filterCustomerMono, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest serverRequest){
        Mono<Customer> inputCustomerMono = serverRequest.bodyToMono(Customer.class);
        Mono<String> savedResponse = inputCustomerMono.map(customer -> "SAVED : " + customer.custId() + "-" + customer.customerName());
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(savedResponse, String.class);
    }



}
