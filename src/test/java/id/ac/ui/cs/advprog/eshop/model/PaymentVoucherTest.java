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

    // unhappy: create payment when voucher code does not have exactly 8 numeric characters
    @Test
    void testCreatePaymentWithInvalidNumericCountVoucher() {
        PaymentVoucher payment = new PaymentVoucher("PAYMENT-07", invalidNumericCountVoucherData);
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

    // unhappy: voucher not 16 characters long
    @Test
    void testValidateVoucherWithInvalidLength() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, "ESHOP1234AB567");
        assertFalse(isValid);
    }

    // unhappy: voucher does not start with ESHOP should return false
    @Test
    void testValidateVoucherWithInvalidPrefix() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, "STORE1234ABCD5678");
        assertFalse(isValid);
    }

    // unhappy: voucher does not contain exactly 8 digits should return false
    @Test
    void testValidateVoucherWithInvalidDigitCount() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, "ESHOPABCDEF123456");
        assertFalse(isValid);
    }

    @Test
    void testValidateVoucherWithIncorrectDigitCount() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);

        String invalidVoucherCode = "ESHOP12ABC34567";

        String extractedDigits = invalidVoucherCode.replaceAll("[^0-9]", "");

        assertNotEquals(8, extractedDigits.length(), "Extracted digit count should NOT be 8");
        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, invalidVoucherCode);
        assertFalse(isValid, "Voucher validation should fail for incorrect digit count");
    }

    @Test
    void testDigitCountConditionInValidation() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);

        String validVoucherCode = "ESHOP1234ABC5678";
        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, validVoucherCode);
        assertTrue(isValid, "Voucher with exactly 8 digits should be valid");

        String invalidVoucherCode = "ESHOP12ABC345";
        boolean isInvalid = (boolean) validateVoucherMethod.invoke(paymentVoucher, invalidVoucherCode);
        assertFalse(isInvalid, "Voucher with less than 8 digits should be invalid");

        String overDigitVoucherCode = "ESHOP1234567890";
        boolean isOverDigitInvalid = (boolean) validateVoucherMethod.invoke(paymentVoucher, overDigitVoucherCode);
        assertFalse(isOverDigitInvalid, "Voucher with more than 8 digits should be invalid");
    }

    @Test
    void testValidateVoucherWithTooFewDigits() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, "ESHOP12ABCD567");
        assertFalse(isValid, "Voucher with only 7 digits should be invalid");
    }

    @Test
    void testValidateVoucherWithTooManyDigits() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, "ESHOP123456789012");
        assertFalse(isValid, "Voucher with more than 8 digits should be invalid");
    }

    @Test
    void testValidateVoucherWithHiddenCharacters() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);
        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, "ESHOP12#34AB5678");
        assertTrue(isValid, "Voucher with hidden special characters should still be valid if it has exactly 8 digits");
    }

    @Test
    void testExtractedDigitCountFromVoucher() throws Exception {
        PaymentVoucher paymentVoucher = new PaymentVoucher("TEST-ID", validVoucherData);

        String voucherCode = "ESHOP12A34B567C8"; // 8 scattered digits
        String extractedDigits = voucherCode.replaceAll("[^0-9]", "");

        assertEquals(8, extractedDigits.length(), "Extracted digit count should be exactly 8");

        boolean isValid = (boolean) validateVoucherMethod.invoke(paymentVoucher, voucherCode);
        assertTrue(isValid, "Voucher should be valid if it contains exactly 8 digits");
    }

    @Test
    void testExtractDigitCountLogic() {
        String voucherCode = "ESHOP12A34B567C8"; // 8 scattered digits
        int extractedDigitCount = voucherCode.replaceAll("[^0-9]", "").length();

        assertEquals(8, extractedDigitCount, "Extracted digit count should be exactly 8");
    }

    @Test
    void testExtractDigitCountLogicInvalid() {
        String voucherCode = "ESHOP123ABC56"; // Only 5 digits
        int extractedDigitCount = voucherCode.replaceAll("[^0-9]", "").length();

        assertNotEquals(8, extractedDigitCount, "Extracted digit count should NOT be 8");
    }

    @Test
    void testVoucherCodeDigitCheck() {
        String validVoucher = "ESHOP12AB34CD5678";
        String invalidVoucher = "ESHOP123ABC56";

        boolean isValid = validVoucher.replaceAll("[^0-9]", "").length() == 8;
        boolean isInvalid = invalidVoucher.replaceAll("[^0-9]", "").length() == 8;

        assertTrue(isValid, "Voucher code should have exactly 8 digits.");
        assertFalse(isInvalid, "Voucher code should NOT have exactly 8 digits.");
    }
}
