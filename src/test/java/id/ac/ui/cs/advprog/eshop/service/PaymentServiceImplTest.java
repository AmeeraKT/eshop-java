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
    List<Payment> payments;
    private Map<String, String> validVoucherData;
    private Map<String, String> invalidVoucherData;
    Payment paymentDummyData;

    @BeforeEach
    void setUp() {

        // product dummy data
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("PRODUCT-01");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        // order dummy data
        order = new Order("ORDER-01", products, 1709560000L, "John Doe");

        // payment dummy data
        payments = new ArrayList<>();
        Payment payment1 = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), validVoucherData);
        payments.add(payment1);
        Payment payment2 = new Payment("PAYMENT-02", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), validVoucherData);
        payments.add(payment2);

        // for happy tests
        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        // for unhappy tests
        invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "NOTESHOP1234ABC5678");

        paymentDummyData = payments.get(0);
    }

    // happy: add payment with a valid voucher
    @Test
    void testAddPaymentWithValidVoucher() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentDummyData);

        Payment result = paymentService.addPayment(order, PaymentMethod.VOUCHER.getMethod(), validVoucherData);

        assertNotNull(result);
        assertEquals(paymentDummyData.getId(), result.getId());
        assertEquals(paymentDummyData.getMethod(), result.getMethod());
        assertEquals(paymentDummyData.getStatus(), result.getStatus());

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
        Payment payment = paymentDummyData;
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        assertThrows(IllegalArgumentException.class, () -> {paymentService.setStatus(payment, "MEOW");});
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    // happy: edit status of payment with valid status ( "FAILED" )
    @Test
    void testSetStatusRejected() {
        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentDummyData);

        Payment updatedPayment = paymentService.setStatus(paymentDummyData, PaymentStatus.REJECTED.getStatus());

        assertEquals(PaymentStatus.REJECTED.getStatus(), updatedPayment.getStatus());
        verify(paymentRepository, times(1)).save(updatedPayment);
    }

    // happy: edit status of payment with valid status ( "SUCCESS" )
    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(1);

        assertNotNull(paymentDummyData, "There is no dummy data.");

        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        when(orderRepository.findById(payment.getId())).thenReturn(order);

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order mockOrder = new Order(paymentDummyData.getId(), new ArrayList<>(), System.currentTimeMillis(), "User");
        when(orderRepository.findById(paymentDummyData.getId())).thenReturn(mockOrder);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getStatus());

        assertEquals(payment.getId(), result.getId());
        assertEquals(PaymentStatus.SUCCESS.getStatus(), result.getStatus());
        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    // happy: edit payment status to null status
    @Test
    void testSetStatusWithNull() {
        assertThrows(NoSuchElementException.class, () -> {
            paymentService.setStatus(paymentDummyData, null);
        });

        verify(paymentRepository, never()).save(any(Payment.class));
    }

    // happy: find valid payment by valid ID
    @Test
    void testGetPayment() {
        Payment paymentDummyData = new Payment("PAYMENT-01", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.SUCCESS.getStatus(), validVoucherData);
        when(paymentRepository.findById(paymentDummyData.getId())).thenReturn(paymentDummyData);

        Payment result = paymentService.getPayment(paymentDummyData.getId());

        assertNotNull(result);
        assertEquals(paymentDummyData.getId(), result.getId());
        assertEquals(paymentDummyData.getMethod(), result.getMethod());
        assertEquals(paymentDummyData.getStatus(), result.getStatus());
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
        List<Payment> payments = Collections.singletonList(paymentDummyData);
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(1, result.size());
    }
}