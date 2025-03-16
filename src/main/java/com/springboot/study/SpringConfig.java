package com.springboot.study;

import com.springboot.study.repository.MemberRepository;
import com.springboot.study.repository.MemoryMemberRepository;
import com.springboot.study.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
