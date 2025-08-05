package com.springboot.study.principle;

import com.springboot.study.principle.discount.DiscountPolicy;
import com.springboot.study.principle.discount.RateDiscountPolicy;
import com.springboot.study.principle.member.MemberRepository;
import com.springboot.study.principle.member.MemberService;
import com.springboot.study.principle.member.MemberServiceImpl;
import com.springboot.study.principle.member.MemoryMemberRepository;
import com.springboot.study.principle.order.OrderService;
import com.springboot.study.principle.order.OrderServiceImpl;

public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
