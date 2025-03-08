package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PaymentCashOnDelivery extends Payment {

    public PaymentCashOnDelivery(String id, Map<String, String> paymentData) {
        super(id, PaymentMethod.CASH_ON_DELIVERY.getMethod(), PaymentStatus.PENDING.getStatus(), paymentData);
        validatePayment(paymentData);

        if (paymentData == null || paymentData.isEmpty()) {
            throw new IllegalArgumentException("Payment data cannot be empty for Cash On Delivery");
        }

        if (!validateCOD(paymentData)) {
            this.setStatus(PaymentStatus.REJECTED.getStatus());
        }
    }

    private void validatePayment(Map<String, String> paymentData) {
        if (!validateCOD(paymentData)) {
            this.status = PaymentStatus.REJECTED.getStatus();
        }
    }

    private boolean validateCOD(Map<String, String> paymentData) {
        return validateField(paymentData, "address") && validateField(paymentData, "deliveryFee");
    }

    private boolean validateField(Map<String, String> data, String key) {
        return data.get(key) != null && !data.get(key).trim().isEmpty();
    }
}
