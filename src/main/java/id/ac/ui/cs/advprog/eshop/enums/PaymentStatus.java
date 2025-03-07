package enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SUCCESS("SUCCESS"),
    REJECTED("FAILED"),
    PENDING("PENDING");

    private final String status;

    private PaymentStatus(String value) {
        this.status = value;
    }

    public static boolean contains(String param) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getStatus().equals(param)) {
                return true;
            }
        }
        return false;
    }
}