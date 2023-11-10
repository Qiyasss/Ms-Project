package com.example.mspayment.controller;

import com.example.mspayment.model.request.PaymentCriteria;
import com.example.mspayment.model.request.PaymentRequest;
import com.example.mspayment.model.response.PageablePaymentResponse;
import com.example.mspayment.model.response.PaymentResponse;
import com.example.mspayment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {



    private final PaymentService paymentService;

    @PostMapping("/save")
    @ResponseStatus(CREATED)
    public void savePayment(@Valid @RequestBody PaymentRequest request) {

        paymentService.savePayment(request);
    }

    @GetMapping("/getAll")
    public PageablePaymentResponse getAllPayments(@RequestParam int page, @RequestParam int count, PaymentCriteria paymentCriteria) {
        return paymentService.getAllPayments(page, count, paymentCriteria);

    }
    @GetMapping("/{id}")
    public PaymentResponse getPaymentById( @PathVariable Long id) {
       return paymentService.getPaymentById(id);

    }

    @PutMapping("/edit/{id}")
    public void updatePayment (@PathVariable Long id, @RequestBody PaymentRequest request){
        paymentService.updatePayment(id, request);

    }

    @DeleteMapping("/delete/{id}")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePaymentById(id);
    }


}
