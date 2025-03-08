package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private Map<String, String> validVoucherData;
    private Map<String, String> invalidVoucherData;

    @BeforeEach
    void setUp() {

        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "NOTESHOP1234ABC5678");
    }

    // happy: create payment with valid status
    @Test
    void testCreatePaymentWithValidStatus() {
        Payment payment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), validVoucherData);

        assertEquals(PaymentStatus.SUCCESS.getStatus(), payment.getStatus());
    }

    // unhappy: create payment with invalid status
    @Test
    void testCreatePaymentWithInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), "MEOW", validVoucherData);
        });
    }

    // happy: edit payment status with valid status
    @Test
    void testEditStatusToValidStatus() {
        Payment payment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.PENDING.getStatus(), validVoucherData);
        payment.setStatus("SUCCESS");

        assertEquals(PaymentStatus.SUCCESS.getStatus(), payment.getStatus());
    }

    // unhappy: edit payment status with invalid status
    @Test
    void testEditStatusToInvalidStatus() {
        Payment payment = new Payment("PAYMENT-01",PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), validVoucherData);

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }

    // happy: create payment with valid method
    @Test
    void testCreatePaymentValidMethod() {
        Payment payment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), validVoucherData);

        assertEquals("voucherCode", payment.getMethod());
    }

    // unhappy: create payment with invalid method
    @Test
    void testCreatePaymentWithInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("PAYMENT-01", "MEOW", PaymentStatus.SUCCESS.getStatus(), validVoucherData);
        });
    }
}
