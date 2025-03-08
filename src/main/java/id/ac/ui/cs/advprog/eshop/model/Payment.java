package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;

import java.util.Map;

@Getter
@Setter
public class Payment {

    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {

        validatePayment(method, paymentData);
        if (!PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method: " + method);
        }

        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status: " + status);
        }

        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;

        // logic for changing status
        if (PaymentMethod.VOUCHER.getMethod().equals(method) && !validateVoucher(paymentData.get("voucherCode"))) {
            this.status = PaymentStatus.REJECTED.getStatus();
        } else {
            this.status = status;
        }
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean validateVoucher(String voucherCode) {
        return voucherCode != null &&
        // code feature rules
                voucherCode.length() == 16 &&
                voucherCode.startsWith("ESHOP") &&
                voucherCode.replaceAll("[^0-9]", "").length() == 8;
    }

    public void validatePayment(String method, Map<String, String> paymentData) {
        if (PaymentMethod.VOUCHER.getMethod().equals(method)) {

            if (paymentData == null || !paymentData.containsKey("voucherCode")) {
                throw new IllegalArgumentException("Voucher code is required for VOUCHER payment");
            }

            String voucherCode = paymentData.get("voucherCode");

            if (!validateVoucher(voucherCode)) {
                throw new IllegalArgumentException("Invalid voucher code: " + voucherCode);
            }
        }
    }
}