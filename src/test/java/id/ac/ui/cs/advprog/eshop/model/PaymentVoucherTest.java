package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentVoucherTest {

    private Map<String, String> validVoucherData;
    private Map<String, String> invalidVoucherData;
    private Map<String, String> invalidPrefixVoucherData;
    private Map<String, String> invalidNumericCountVoucherData;
    private Method validateVoucherMethod;

    @BeforeEach
    void setUp() throws Exception {
        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "NOTESHOP1234ABC5678");

        invalidPrefixVoucherData = new HashMap<>();
        invalidPrefixVoucherData.put("voucherCode", "STORE1234ABC5678");

        invalidNumericCountVoucherData = new HashMap<>();
        invalidNumericCountVoucherData.put("voucherCode", "ESHOPABCDEF123456");

        // access validateVoucher method
        validateVoucherMethod = PaymentVoucher.class.getDeclaredMethod("validateVoucher", String.class);
        validateVoucherMethod.setAccessible(true);
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

    // unhappy: create payment with null payment data
    @Test
    void testCreatePaymentWithNullData() {
        assertThrows(IllegalArgumentException.class, () -> new PaymentVoucher("PAYMENT-04", null));
    }
    // unhappy: create payment when voucher is not 16 digits long
    @Test
    void testCreatePaymentWithInvalidLengthVoucher() {
        PaymentVoucher payment = new PaymentVoucher("PAYMENT-05", invalidVoucherData);
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus());
    }

    // unhappy: create payment when voucher code does not start with "ESHOP"
    @Test
    void testCreatePaymentWithInvalidPrefixVoucher() {
        PaymentVoucher payment = new PaymentVoucher("PAYMENT-06", invalidPrefixVoucherData);
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus());
    }

    @Test
    void testValidateVoucherWithValidCode() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        String voucherCode = "ESHOP1234ABC5678";

        String extractedDigits = voucherCode.replaceAll("[^0-9]", "");
        assertEquals(8, extractedDigits.length(), "Extracted digit count is incorrect");

        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, voucherCode);
        assertTrue(isValid, "Voucher validation failed for a valid voucher code");
    }

    // unhappy: null voucher
    @Test
    void testValidateVoucherWithNullCode() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, (Object) null);
        assertFalse(isValid);
    }
}
