package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentMethod;
import enums.PaymentStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentTest {

    private List<Product> products;
    private Order order;
    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        // product dummy data
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        // dummy order data
        order = new Order("ORDER-O1", products, 1708560000L, "John Doe");

        this.paymentData = new HashMap<String, String>();
    }

    // happy: create payment with valid status
    @Test
    void testCreatePaymentWithValidStatus() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), paymentData);

        assertEquals(PaymentStatus.SUCCESS.getStatus(), payment.getStatus());
    }

    // unhappy: create payment with invalid status
    @Test
    void testCreatePaymentWithInvalidStatus() {
        paymentData.put("voucherCode", "NOTESHOP1234ABC5678");

        assertThrows(IllegalArgumentException.class, () -> {
            Payment payment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), "MEOW", paymentData);
        });
    }

    // happy: edit payment status with valid status
    @Test
    void testEditStatusToValidStatus() {
        Payment payment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), paymentData);
        payment.setStatus("FAILED");
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus());
    }

    // unhappy: edit payment status with invalid status
    @Test
    void testEditStatusToInvalidStatus() {
        Payment payment = new Payment("PAYMENT-01",PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), paymentData);

        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }

    // happy: create payment with valid method
    @Test
    void testCreatePaymentValidMethod() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), paymentData);

        assertEquals("voucherCode", payment.getMethod());
    }
}
