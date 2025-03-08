package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentVoucherTest {

    private Map<String, String> validVoucherData;
    private Map<String, String> invalidVoucherData;

    @BeforeEach
    void setUp() {
        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "NOTESHOP1234ABC5678");
    }

    // happy: create payment with valid voucher
    @Test
    void testCreatePaymentWithValidVoucher() {
        PaymentVoucher payment = new PaymentVoucher("PAYMENT-01", validVoucherData);

        assertNotNull(payment);
        assertEquals(PaymentMethod.VOUCHER.getMethod(), payment.getMethod());
        assertEquals(PaymentStatus.PENDING.getStatus(), payment.getStatus()); // Default status is PENDING
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    // unhappy: create payment with invalid voucher
    @Test
    void testCreatePaymentWithInvalidVoucher() {
        PaymentVoucher payment = new PaymentVoucher("PAYMENT-02", invalidVoucherData);
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus()); // Should be REJECTED
    }

    // unhappy: create payment without voucher code
    @Test
    void testCreatePaymentWithoutVoucherCode() {
        Map<String, String> emptyData = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> new PaymentVoucher("PAYMENT-03", emptyData));
    }
}
