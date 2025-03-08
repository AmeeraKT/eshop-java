package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class PaymentVoucher extends Payment {

    private static final String REQUIRED_PREFIX = "ESHOP";
    private static final int REQUIRED_LENGTH = 16;
    private static final int REQUIRED_DIGIT_COUNT = 8;

    public PaymentVoucher(String id, Map<String, String> paymentData) {
        super(id, PaymentMethod.VOUCHER.getMethod(), PaymentStatus.PENDING.getStatus(), paymentData);

        if (paymentData == null || !paymentData.containsKey("voucherCode")) {
            throw new IllegalArgumentException("Voucher code is required for VOUCHER payment");
        }

        String voucherCode = paymentData.get("voucherCode");

        if (!isValidVoucherCode(voucherCode)) {
            this.setStatus(PaymentStatus.REJECTED.getStatus());
        }
    }

    protected boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null) {
            return false;
        }
        if (!hasValidLength(voucherCode)) {
            return false;
        }
        if (!hasValidPrefix(voucherCode)) {
            return false;
        }
        return hasValidDigitCount(voucherCode);
    }

    protected boolean hasValidLength(String voucherCode) {
        return voucherCode.length() == REQUIRED_LENGTH;
    }

    protected boolean hasValidPrefix(String voucherCode) {
        return voucherCode.startsWith(REQUIRED_PREFIX);
    }

    protected boolean hasValidDigitCount(String voucherCode) {
        return countDigits(voucherCode) == REQUIRED_DIGIT_COUNT;
    }

    protected int countDigits(String input) {
        if (input == null) return 0;
        return input.replaceAll("[^0-9]", "").length();
    }
}