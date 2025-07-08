package com.springboot.study.principle.discount;

import com.springboot.study.principle.member.Grade;
import com.springboot.study.principle.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000; // 1000원 할인

    @Override
    public int discount(Member member, int price) {
        if (member.getGraade() == Grade.VIP) {
            return discountFixAmount;
        } else {
            return 0;
        }
    }
}
