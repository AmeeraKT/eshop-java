package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CashOnDeliveryPaymentTest {

    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
    }

    // happy: create COD payment with valid data
    @Test
    void testCashOnDeliveryPaymentWithValidData() {
        Map<String, String> validCODData = new HashMap<>();
        validCODData.put("address", "Jl. Prof. DR. Sudjono D. Pusponegoro, Pondok Cina, Kecamatan Beji, Kota Depok, Jawa Barat 16425");
        validCODData.put("deliveryFee", "$8.00");

        Payment payment = new Payment("PAYMENT-01", PaymentMethod.CASH_ON_DELIVERY.name(), PaymentStatus.SUCCESS.getStatus(), validCODData);

        assertEquals(PaymentMethod.CASH_ON_DELIVERY.name(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getStatus(), payment.getStatus());
        assertEquals(validCODData, payment.getPaymentData());
    }

    // unhappy: create COD payment with empty data
    @Test
    void testEmptyPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> new Payment("PAYMENT-01", PaymentMethod.CASH_ON_DELIVERY.name(), PaymentStatus.PENDING.getStatus(), new HashMap<>()));
    }

    // unhappy: create COD payment with empty address
    @Test
    void testCashOnDeliveryPaymentWithEmptyAddress() {
        Map<String, String> invalidCODData = new HashMap<>();
        invalidCODData.put("address", "");
        invalidCODData.put("deliveryFee", "$8.00");

        Payment payment = new Payment("PAYMENT-01", PaymentMethod.CASH_ON_DELIVERY.name(), PaymentStatus.PENDING.getStatus(), invalidCODData);
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus());
    }

    // unhappy: create COD payment with empty fee
    @Test
    void testCashOnDeliveryPaymentWithEmptyFee() {
        Map<String, String> invalidCODData = new HashMap<>();
        invalidCODData.put("address", "Jl. Prof. DR. Sudjono D. Pusponegoro, Pondok Cina, Kecamatan Beji, Kota Depok, Jawa Barat 16425");
        invalidCODData.put("deliveryFee", "");

        Payment payment = new Payment("PAYMENT-01", PaymentMethod.CASH_ON_DELIVERY.name(), PaymentStatus.PENDING.getStatus(), invalidCODData);
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus());
    }

    // unhappy: create COD payment with missing address
    @Test
    void testMissingAddressPaymentData() {
        Map<String, String> invalidCODData = new HashMap<>();
        invalidCODData.put("deliveryFee", "$8.00");

        Payment payment = new Payment("PAYMENT-01", PaymentMethod.CASH_ON_DELIVERY.name(), PaymentStatus.PENDING.getStatus(), invalidCODData);
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus());
    }

    // unhappy: create COD payment with missing fee
    @Test
    void testMissingDeliveryFeePaymentData() {
        Map<String, String> invalidCODData = new HashMap<>();
        invalidCODData.put("address", "Somewhere over the Rainbow, Lalaland");

        Payment payment = new Payment("PAYMENT-01", PaymentMethod.CASH_ON_DELIVERY.name(), PaymentStatus.PENDING.getStatus(), invalidCODData);
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus());
    }
}