package com.springboot.study.principle;

import com.springboot.study.principle.member.Grade;
import com.springboot.study.principle.member.Member;
import com.springboot.study.principle.member.MemberService;
import com.springboot.study.principle.order.Order;
import com.springboot.study.principle.order.OrderService;

public class OrderApp {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10_000);

        System.out.println("order = " + order);
    }
}
