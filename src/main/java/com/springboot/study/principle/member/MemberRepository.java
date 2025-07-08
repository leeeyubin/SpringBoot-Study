package com.springboot.study.principle.member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
