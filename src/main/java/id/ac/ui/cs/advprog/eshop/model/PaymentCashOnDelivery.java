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
        return null;
    }

    private void validatePayment(Map<String, String> paymentData) {
        return null;
    }

    private boolean validateCOD(Map<String, String> paymentData) {
        return null;
    }

    private boolean validateField(Map<String, String> data, String key) {
        return null;
    }
}
