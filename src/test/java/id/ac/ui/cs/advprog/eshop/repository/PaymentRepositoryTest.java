package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Payment testPayment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setup() {
        paymentRepository = new PaymentRepository();
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        testPayment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), paymentData);
    }

    // happy: create and save payment
    @Test
    void testSaveCreate() {
        Payment result = paymentRepository.save(testPayment);
        assertEquals(testPayment.getId(), result.getId());

        Payment findResult = paymentRepository.findById(testPayment.getId());

        assertEquals(testPayment.getId(), result.getId());
        assertEquals(testPayment.getId(), findResult.getId());
        assertEquals(testPayment.getMethod(), findResult.getMethod());
        assertEquals(testPayment.getStatus(), findResult.getStatus());
    }

    // happy: save and update payment
    @Test
    void testSaveUpdate() {
        paymentRepository.save(testPayment);

        Payment newPayment = new Payment(testPayment.getId(), PaymentMethod.VOUCHER.getMethod(),
                PaymentStatus.REJECTED.getStatus(), paymentData);
        Payment result = paymentRepository.save(newPayment);

        Payment findResult = paymentRepository.findById(testPayment.getId());

        assertEquals(testPayment.getId(), result.getId());
        assertEquals(testPayment.getId(), findResult.getId());
        assertEquals(testPayment.getMethod(), findResult.getMethod());
        assertEquals(PaymentStatus.REJECTED.getStatus(), findResult.getStatus());
    }

    // happy: find payment with valid ID
    @Test
    void testFindByIdIfIdFound() {
        paymentRepository.save(testPayment);

        Payment findResult = paymentRepository.findById(testPayment.getId());

        assertEquals(testPayment.getId(), findResult.getId());
        assertEquals(testPayment.getStatus(), findResult.getStatus());
        assertEquals(testPayment.getMethod(), findResult.getMethod());
    }

    // find payment with invalid ID
    @Test
    void testFindByIdIfIdNotFound() {
        Payment findResult = paymentRepository.findById("OHNOWRONGID");

        assertNull(findResult);
    }

    // Test saving an existing payment to cover loop iteration
    @Test
    void testSaveUpdateExistingPayment() {
        paymentRepository.save(testPayment);

        Payment updatedPayment = new Payment(testPayment.getId(), PaymentMethod.VOUCHER.getMethod(),
                PaymentStatus.REJECTED.getStatus(), paymentData);

        Payment result = paymentRepository.save(updatedPayment);

        assertEquals(updatedPayment.getId(), result.getId());
        assertEquals(PaymentStatus.REJECTED.getStatus(), result.getStatus());
        assertEquals(PaymentMethod.VOUCHER.getMethod(), result.getMethod());
    }

    // Test findById to ensure loop iteration covers finding an existing ID
    @Test
    void testFindByIdCoversLoop() {
        paymentRepository.save(testPayment);

        Payment anotherPayment = new Payment("PAYMENT-02", PaymentMethod.CASH_ON_DELIVERY.getMethod(),
                PaymentStatus.SUCCESS.getStatus(), paymentData);
        paymentRepository.save(anotherPayment);

        Payment findResult = paymentRepository.findById("PAYMENT-02");

        assertNotNull(findResult);
        assertEquals("PAYMENT-02", findResult.getId());
        assertEquals(PaymentMethod.CASH_ON_DELIVERY.getMethod(), findResult.getMethod());
    }

    // Test findAll to ensure it returns all stored payments
    @Test
    void testFindAllCoversReturnedList() {
        assertTrue(paymentRepository.findAll().isEmpty());

        paymentRepository.save(testPayment);

        Payment anotherPayment = new Payment("PAYMENT-02", PaymentMethod.VOUCHER.getMethod(),
                PaymentStatus.SUCCESS.getStatus(), paymentData);
        paymentRepository.save(anotherPayment);

        assertEquals(2, paymentRepository.findAll().size());
    }
}