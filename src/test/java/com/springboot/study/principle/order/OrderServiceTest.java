package com.springboot.study.principle.order;

import com.springboot.study.principle.AppConfig;
import com.springboot.study.principle.member.Grade;
import com.springboot.study.principle.member.Member;
import com.springboot.study.principle.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        Long memberId = 1L;
        Member member =  new Member (memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10_000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1_000);
    }
}
