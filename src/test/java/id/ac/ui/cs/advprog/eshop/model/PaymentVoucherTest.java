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
        invalidVoucherData.put("voucherCode", "STORE1234ABC5678");
    }

    // Happy path: create payment with valid voucher
    @Test
    void testCreatePaymentWithValidVoucher() {
        PaymentVoucher payment = new PaymentVoucher("PAYMENT-01", validVoucherData);
        assertNotNull(payment);
        assertEquals(PaymentMethod.VOUCHER.getMethod(), payment.getMethod());
        assertEquals(PaymentStatus.PENDING.getStatus(), payment.getStatus());
    }

    // Unhappy path: create payment with invalid voucher
    @Test
    void testCreatePaymentWithInvalidVoucher() {
        PaymentVoucher payment = new PaymentVoucher("PAYMENT-02", invalidVoucherData);
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus());
    }

    // Unhappy path: create payment without voucher code
    @Test
    void testCreatePaymentWithoutVoucherCode() {
        Map<String, String> emptyData = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> new PaymentVoucher("PAYMENT-03", emptyData));
    }

    // Unhappy path: create payment with null payment data
    @Test
    void testCreatePaymentWithNullData() {
        assertThrows(IllegalArgumentException.class, () -> new PaymentVoucher("PAYMENT-04", null));
    }

    // Validation tests
    @Test
    void testValidVoucherCode() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        String voucherCode = "ESHOP1234ABC5678";
        assertTrue(paymentVoucher.isValidVoucherCode(voucherCode));
    }

    @Test
    void testInvalidLengthVoucher() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        String voucherCode = "ESHOP123ABC567"; // Too short
        assertFalse(paymentVoucher.isValidVoucherCode(voucherCode));
    }

    @Test
    void testInvalidPrefixVoucher() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        String voucherCode = "STORE1234ABC5678"; // Wrong prefix
        assertFalse(paymentVoucher.isValidVoucherCode(voucherCode));
    }

    @Test
    void testInvalidDigitCountVoucher() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        String voucherCode = "ESHOP12ABC5678"; // Only 6 digits
        assertFalse(paymentVoucher.isValidVoucherCode(voucherCode));
    }

    @Test
    void testNullVoucherCode() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);

        assertFalse(paymentVoucher.isValidVoucherCode(null));
    }

    @Test
    void testCountDigits() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);

        assertEquals(8, paymentVoucher.countDigits("ESHOP1234ABC5678"));
        assertEquals(0, paymentVoucher.countDigits("ESHOPABC"));
        assertEquals(10, paymentVoucher.countDigits("ESHOP1234567890ABC"));
    }

    @Test
    void testInvalidDigitCount() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);

        assertFalse(paymentVoucher.hasValidDigitCount("ESHOP12ABC5678"));
        assertFalse(paymentVoucher.hasValidDigitCount("ESHOP1234ABC56789"));
        assertTrue(paymentVoucher.hasValidDigitCount("ESHOP1234ABC5678"));
    }

    @Test
    void testInvalidDigitCountCallValidation6Digits() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);

        String voucherCode = "ESHOP12ABC5678"; // Only 6 digits

        assertFalse(paymentVoucher.isValidVoucherCode(voucherCode));
    }

    @Test
    void testInvalidDigitCountCallValidation9Digits() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);

        String voucherCode = "ESHOP1234ABC56789"; // 9 digits

        assertFalse(paymentVoucher.isValidVoucherCode(voucherCode));
    }

    @Test
    void testValidDigitCount() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);

        assertTrue(paymentVoucher.hasValidDigitCount("ESHOP1234ABC5678"));
    }

    @Test
    void testCountDigitsWithNullInput() {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);

        assertEquals(0, paymentVoucher.countDigits(null));
    }

}