package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    private List<Product> products;
    private Order order;
    private Payment payment;
    private Map<String, String> paymentData;
    private Map<String, String> validVoucherData;
    private Map<String, String> invalidVoucherData;


    @BeforeEach
    void setUp() {

        this.paymentData = new HashMap<String, String>();

        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "NOTESHOP1234ABC5678");

        // product dummy data
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        // dummy order data
        order = new Order("ORDER-O1", products, 1708560000L, "John Doe");

        this.paymentData = new HashMap<>();
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

    // happy: create payment with valid voucher
    @Test
    void testCreatePaymentWithValidVoucher() {
        Payment payment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), validVoucherData);

        assertNotNull(payment);
        assertEquals(PaymentMethod.VOUCHER.getMethod(), payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getStatus(), payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    // unhappy: create payment with invalid voucher
    @Test
    void testCreatePaymentWithInvalidVoucher() {
        Payment payment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.REJECTED.getStatus(), invalidVoucherData);

        assertEquals(PaymentMethod.VOUCHER.getMethod(), payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus());
    }
}
