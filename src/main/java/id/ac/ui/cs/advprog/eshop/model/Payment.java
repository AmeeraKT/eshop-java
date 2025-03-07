package id.ac.ui.cs.advprog.eshop.model;

import lombok.Setter;

import java.util.Map;

public class Payment {

    String id;
    String method;
    @Setter
    String status;
    Map <String, String> paymentData;

    public Payment(String id, String method,  String status, Map <String, String> paymentData) {
    }

    public void setStatus(String status) {
    }
}
