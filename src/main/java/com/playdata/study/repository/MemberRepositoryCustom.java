package com.playdata.study.repository;

import com.playdata.study.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    // JPA꺼 아님! 상속 안받음!
    List<Member> findByName(String name);
}
