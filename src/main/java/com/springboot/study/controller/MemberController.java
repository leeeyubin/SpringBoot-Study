package com.springboot.study.controller;

import com.springboot.study.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    // 1) 필드 주입
    // @Autowired private MemberService memberService;

    private final MemberService memberService;

    // 2) 생성자 주입
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//    3) 세터 주입
//    @Autowired
//    public void setMemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }
}
