package id.ac.ui.cs.advprog.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    VOUCHER("voucherCode"),
    CASH_ON_DELIVERY("Cash On Delivery");

    private final String method;

    private PaymentMethod(String value) {
        this.method = value;
    }

    public static boolean contains(String param) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.getMethod().equals(param)) {
                return true;
            }
        }
        return false;
    }
}