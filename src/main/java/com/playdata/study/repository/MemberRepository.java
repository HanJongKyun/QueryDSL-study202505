package com.playdata.study.repository;

import com.playdata.study.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository <Member, Long>, MemberRepositoryCustom {

//    @Query ("select m from Member m where m.userName =:userName")
//    Optional<Member> findMember(@Param("userName") String userName);
}
