package com.example.mspayment.mapper;

import com.example.mspayment.entity.Payment;
import com.example.mspayment.model.request.PaymentRequest;
import com.example.mspayment.model.response.PaymentResponse;

import java.time.LocalDateTime;

public class PaymentMapper {


    public static Payment requestToEntity(PaymentRequest paymentRequest) {

        return Payment.builder().
                amount(paymentRequest.getAmount()).
                description(paymentRequest.getDescription()).
                build();

    }

    public static PaymentResponse entityToResponse (Payment payment){

        return PaymentResponse.builder().
                id(payment.getId()).
                amount(payment.getAmount()).
                description(payment.getDescription()).responseAt(LocalDateTime.now()).
                build();

    }

}
