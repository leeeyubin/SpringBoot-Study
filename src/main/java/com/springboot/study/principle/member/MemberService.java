package com.springboot.study.principle.member;

public interface MemberService {

    void join(Member member);

    Member findMember(Long memberId);
}
