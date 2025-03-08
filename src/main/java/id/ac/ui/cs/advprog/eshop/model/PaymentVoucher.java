package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.Map;

public class PaymentVoucher extends Payment {

    public PaymentVoucher(String id, Map<String, String> paymentData) {
        super(id, PaymentMethod.VOUCHER.getMethod(), PaymentStatus.PENDING.getStatus(), paymentData);

        if (paymentData == null || !paymentData.containsKey("voucherCode")) {
            throw new IllegalArgumentException("Voucher code is required for VOUCHER payment");
        }

        String voucherCode = paymentData.get("voucherCode");

        if (!validateVoucher(voucherCode)) {
            this.setStatus(PaymentStatus.REJECTED.getStatus());
        }
    }

    private boolean validateVoucher(String voucherCode) {
        return voucherCode != null &&
                voucherCode.length() == 16 &&
                voucherCode.startsWith("ESHOP") &&
                voucherCode.replaceAll("[^0-9]", "").length() == 8;
    }
}