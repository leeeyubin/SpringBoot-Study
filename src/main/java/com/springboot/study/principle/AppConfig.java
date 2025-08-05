package com.springboot.study.principle;

import com.springboot.study.principle.discount.DiscountPolicy;
import com.springboot.study.principle.discount.RateDiscountPolicy;
import com.springboot.study.principle.member.MemberRepository;
import com.springboot.study.principle.member.MemberService;
import com.springboot.study.principle.member.MemberServiceImpl;
import com.springboot.study.principle.member.MemoryMemberRepository;
import com.springboot.study.principle.order.OrderService;
import com.springboot.study.principle.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
