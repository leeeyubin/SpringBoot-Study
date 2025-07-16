package com.springboot.study.principle;

import com.springboot.study.principle.discount.FixDiscountPolicy;
import com.springboot.study.principle.member.MemberService;
import com.springboot.study.principle.member.MemberServiceImpl;
import com.springboot.study.principle.member.MemoryMemberRepository;
import com.springboot.study.principle.order.OrderService;
import com.springboot.study.principle.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

}
