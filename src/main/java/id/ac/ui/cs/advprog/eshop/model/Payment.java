package id.ac.ui.cs.advprog.eshop.model;

import lombok.Setter;


import java.util.List;
import java.util.Map;

public class Payment {

    String id;
    String method;
    @Setter
    String status;
    Map <String, String> paymentData;

    public Payment(String id, List <Order> orders,  String status) {
    }

    public Payment(String id, List <Order> orders, String method,  String status, Map <String, String> paymentData) {
    }

    public void setStatus(String status) {
    }
}
