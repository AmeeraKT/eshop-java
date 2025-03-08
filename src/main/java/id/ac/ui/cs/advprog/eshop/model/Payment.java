package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Payment {

    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
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
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid payment status");
        }
    }
}
