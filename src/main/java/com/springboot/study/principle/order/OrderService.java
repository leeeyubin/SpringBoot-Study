package com.springboot.study.principle.order;

public interface OrderService {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
