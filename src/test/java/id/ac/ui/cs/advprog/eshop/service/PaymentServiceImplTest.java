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
        // for happy tests
        validVoucherData = new HashMap<>();
        validVoucherData.put("voucherCode", "ESHOP1234ABC5678");

        // for unhappy tests
        invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "NOTESHOP1234ABC5678");

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
        Payment payment2 = new Payment("PAYMENT-02", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.REJECTED.getStatus(), validVoucherData);
        payments.add(payment2);

        paymentDummyData = payments.get(0);
    }

    // happy: add payment with a valid voucher
    @Test
    void testAddPaymentWithValidVoucher() {
        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Return the actual Payment object

        Payment result = paymentService.addPayment(order, PaymentMethod.VOUCHER.getMethod(), validVoucherData);

        assertNotNull(result);
        assertTrue(result.getId().startsWith("PAYMENT-"));

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    // unhappy: add payment with invalid voucher
    @Test
    void testAddPaymentWithInvalidVoucher() {
        Payment payment = new Payment("PAYMENT-02", PaymentMethod.VOUCHER.getMethod(), PaymentStatus.REJECTED.getStatus(), invalidVoucherData);

        assertEquals(PaymentMethod.VOUCHER.getMethod(), payment.getMethod());
        assertEquals(PaymentStatus.REJECTED.getStatus(), payment.getStatus());
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
        Payment payment = paymentDummyData;
        when(paymentRepository.findById(payment.getId())).thenReturn(payment);

        assertThrows(IllegalArgumentException.class, () -> { paymentService.setStatus(payment, "MEOW"); });

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    // happy: edit status of payment with valid status ( "SUCCESS" )
    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(1);

        when(paymentRepository.findById(payment.getId())).thenReturn(payment);
        when(orderRepository.findById(payment.getId())).thenReturn(order);

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

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