package com.playdata.study.repository;

import com.playdata.study.dto.SearchDto;
import com.playdata.study.entity.Idol;
import com.playdata.study.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    // JPA꺼 아니에요! 상속 안받았어요!
    List<Member> findByName(String name);

    List<Idol> findIdolByCondition(SearchDto dto);

}






