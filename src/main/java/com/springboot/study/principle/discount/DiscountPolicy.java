package com.springboot.study.principle.discount;

import com.springboot.study.principle.member.Member;

public interface DiscountPolicy {

    /**
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
