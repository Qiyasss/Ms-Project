package com.example.mspayment.service;

import com.example.mspayment.client.CountryClient;
import com.example.mspayment.entity.Payment;
import com.example.mspayment.exception.NotFoundException;
import com.example.mspayment.mapper.PaymentMapper;
import com.example.mspayment.model.request.PaymentCriteria;
import com.example.mspayment.model.request.PaymentRequest;
import com.example.mspayment.model.response.PageablePaymentResponse;
import com.example.mspayment.model.response.PaymentResponse;
import com.example.mspayment.repository.PaymentRepo;
import com.example.mspayment.service.specification.PaymentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.mspayment.mapper.PaymentMapper.entityToResponse;
import static com.example.mspayment.mapper.PaymentMapper.requestToEntity;
import static com.example.mspayment.model.constant.ExceptionConstants.COUNTRY_NOT_FOUND_CODE;
import static com.example.mspayment.model.constant.ExceptionConstants.COUNTRY_NOT_FOUND_MESSAGE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final CountryClient countryClient;

    public void savePayment (PaymentRequest request) {
        log.info("savePayment started");
        countryClient.getAllAvailableCountries(request.getCurrency()).
                stream()
                .filter(country -> country.getRemainingLimit().compareTo(request.getAmount()) > 0)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format(COUNTRY_NOT_FOUND_MESSAGE, request.getAmount(), request.getCurrency()), COUNTRY_NOT_FOUND_CODE));


        paymentRepo.save(requestToEntity(request));
        log.info("savePayment.successed");
    }

    public PageablePaymentResponse getAllPayments(int page, int count, PaymentCriteria paymentCriteria) {

        log.info("getAllPAyments.started");

        var pageable =  PageRequest.of(page, count, Sort.by(DESC, "id"));

        var pageablePayments =  paymentRepo
                .findAll( new PaymentSpecification(paymentCriteria), pageable);

        var payments = pageablePayments.getContent()
                .stream()
                .map(PaymentMapper::entityToResponse).collect(Collectors.toList());

        log.info("getAllPayments.success");

        return  PageablePaymentResponse
                .builder()
                .payments(payments)
                .hasNextPage(pageablePayments.hasNext())
                .totalElements(pageablePayments.getTotalElements())
                .totalPages(pageablePayments.getTotalPages())
                .build();

    }

    public PaymentResponse getPaymentById(Long id) {
        log.info("getPayment.started id:{}", id);
        Payment payment = fetchPaymentIfExist(id);
        log.info("getPayment.successed id : {}", id);
        return entityToResponse(payment);

    }

    public void updatePayment (Long id, PaymentRequest request) {
        log.info("updatePayment.started id:{}", id);
        Payment payment= fetchPaymentIfExist(id);
        payment.setDescription(request.getDescription());
        payment.setAmount(request.getAmount());
        paymentRepo.save(payment);
        log.info("updatePayment.successed id:{} ", id);

    }

    public void deletePaymentById(Long id) {
        log.info("deletePayment.started id : {}", id);
        paymentRepo.deleteById(id);
        log.info("deletePayment.successed id : {}", id);
    }



    private Payment fetchPaymentIfExist(Long id) {
        return paymentRepo.findById(id).orElseThrow(RuntimeException::new);
    }


}
