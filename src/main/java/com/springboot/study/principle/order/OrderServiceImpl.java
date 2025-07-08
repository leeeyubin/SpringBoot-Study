package com.springboot.study.principle.order;

import com.springboot.study.principle.discount.DiscountPolicy;
import com.springboot.study.principle.discount.FixDiscountPolicy;
import com.springboot.study.principle.member.Member;
import com.springboot.study.principle.member.MemberRepository;
import com.springboot.study.principle.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
