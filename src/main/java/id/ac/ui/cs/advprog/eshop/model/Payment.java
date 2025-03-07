package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    private List<String> validMethod = Arrays.asList("voucherCode", "address", "deliveryFee");
    private List<String> validStatus = Arrays.asList("SUCCESS", "REJECTED");

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        if (!validMethod.contains(method)) {
            throw new IllegalArgumentException();
        }

        if (!validStatus.contains(status)) {
            throw new IllegalArgumentException();
        }

        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
    }

    public void setStatus(String status) {
        if (validStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}