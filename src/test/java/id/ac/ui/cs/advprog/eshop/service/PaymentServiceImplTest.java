package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    Order order;
    Payment testPayment;
    private Map<String, String> validVoucherData;
    private Map<String, String> invalidVoucherData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();

        product1.setProductID("PRODUCT-01");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("ORDER-01", products, 1709560000L, "John Doe");

        // for happy tests
        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        // for unhappy tests
        invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "NOTESHOP1234ABC5678");
    }

    // happy: add payment with a valid voucher
    @Test
    void testAddPaymentWithValidVoucher() {
        Payment testPayment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.PENDING.getStatus(), validVoucherData);
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        Payment result = paymentService.addPayment(order, PaymentMethod.VOUCHER.getMethod(), validVoucherData);

        assertNotNull(result);
        assertEquals(testPayment.getId(), result.getId());
        assertEquals(testPayment.getMethod(), result.getMethod());
        assertEquals(testPayment.getStatus(), result.getStatus());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    // unhappy: add payment with invalid voucher
    @Test
    void testAddPaymentWithInvalidVoucher() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.addPayment(order, PaymentMethod.VOUCHER.getMethod(), invalidVoucherData);
        });

        assertEquals("Invalid voucher code", exception.getMessage());
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    // unhappy: edit status of payment with invalid status ("MEOW")
    @Test
    void testSetStatusInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(testPayment, "MEOW");
        });

        assertEquals("Invalid payment status", exception.getMessage());
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    // happy: edit status of payment with valid status ( "FAILED" )
    @Test
    void testSetStatusRejected() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        Payment updatedPayment = paymentService.setStatus(testPayment, PaymentStatus.REJECTED.getStatus());

        assertEquals(PaymentStatus.REJECTED.getStatus(), updatedPayment.getStatus());
        verify(paymentRepository, times(1)).save(updatedPayment);
    }

    // happy: edit status of payment with valid status ( "SUCCESS" )
    @Test
    void testSetStatusSuccess() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(testPayment);

        Payment updatedPayment = paymentService.setStatus(testPayment, PaymentStatus.SUCCESS.getStatus());

        assertEquals(PaymentStatus.SUCCESS.getStatus(), updatedPayment.getStatus());
        verify(paymentRepository, times(1)).save(updatedPayment);
    }

    // happy: edit payment status to null status
    @Test
    void testSetStatusWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(testPayment, null);
        });

        verify(paymentRepository, never()).save(any(Payment.class));
    }

    // happy: find valid payment by valid ID
    @Test
    void testGetPayment() {
        Payment testPayment = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.PENDING.getStatus(), validVoucherData);
        when(paymentRepository.findById("PAYMENT-01")).thenReturn(testPayment);

        Payment result = paymentService.getPayment(testPayment.getId());

        assertNotNull(result);
        assertEquals(testPayment.getId(), result.getId());
        assertEquals(testPayment.getMethod(), result.getMethod());
        assertEquals(testPayment.getStatus(), result.getStatus());
    }

    // unhappy: find payment with invalid ID
    @Test
    void testGetPaymentNotFound() {
        when(paymentRepository.findById("WRONGID")).thenReturn(null);

        Payment result = paymentService.getPayment("WRONGID");
        assertNull(result);
    }

    // happy: find all existing payments
    @Test
    void testGetAllPayments() {
        List<Payment> payments = Collections.singletonList(testPayment);
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(1, result.size());
    }
}