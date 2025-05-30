package com.playdata.study.repository;

import com.playdata.study.dto.SearchDto;
import com.playdata.study.dto.SearchDto;
import com.playdata.study.entity.Album;
import com.playdata.study.entity.Group;
import com.playdata.study.entity.Idol;
import com.playdata.study.entity.QAlbum;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.playdata.study.entity.QGroup.group;
import static com.playdata.study.entity.QIdol.idol;

@SpringBootTest
@Transactional
class QueryDslDynamicTest {

    @Autowired
    IdolRepository idolRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JPAQueryFactory factory;


    @BeforeEach
    void setUp() {
        //given
        Group leSserafim = new Group("르세라핌");
        Group ive = new Group("아이브");
        Group bts = new Group("방탄소년단");
        Group newjeans = new Group("뉴진스");

        groupRepository.save(leSserafim);
        groupRepository.save(ive);
        groupRepository.save(bts);
        groupRepository.save(newjeans);

        Idol idol1 = new Idol("김채원", 24, "여", leSserafim);
        Idol idol2 = new Idol("사쿠라", 26, "여", leSserafim);
        Idol idol3 = new Idol("가을", 22, "여", ive);
        Idol idol4 = new Idol("리즈", 20, "여", ive);
        Idol idol5 = new Idol("장원영", 20, "여", ive);
        Idol idol6 = new Idol("안유진", 21, "여", ive);
        Idol idol7 = new Idol("카즈하", 21, "여", leSserafim);
        Idol idol8 = new Idol("RM", 29, "남", bts);
        Idol idol9 = new Idol("정국", 26, "남", bts);
        Idol idol10 = new Idol("해린", 18, "여", newjeans);
        Idol idol11 = new Idol("혜인", 16, "여", newjeans);
        Idol idol12 = new Idol("김종국", 48, "남", null);
        Idol idol13 = new Idol("아이유", 31, "여", null);


        idolRepository.save(idol1);
        idolRepository.save(idol2);
        idolRepository.save(idol3);
        idolRepository.save(idol4);
        idolRepository.save(idol5);
        idolRepository.save(idol6);
        idolRepository.save(idol7);
        idolRepository.save(idol8);
        idolRepository.save(idol9);
        idolRepository.save(idol10);
        idolRepository.save(idol11);
        idolRepository.save(idol12);
        idolRepository.save(idol13);


        Album album1 = new Album("MAP OF THE SOUL 7", 2020, bts);
        Album album2 = new Album("FEARLESS", 2022, leSserafim);
        Album album3 = new Album("UNFORGIVEN", 2023, bts);
        Album album4 = new Album("ELEVEN", 2021, ive);
        Album album5 = new Album("LOVE DIVE", 2022, ive);
        Album album6 = new Album("OMG", 2023, newjeans);

        albumRepository.save(album1);
        albumRepository.save(album2);
        albumRepository.save(album3);
        albumRepository.save(album4);
        albumRepository.save(album5);
        albumRepository.save(album6);

    }

    @Test
    @DisplayName("동적 쿼리를 사용한 간단한 아이돌 조회")
    void dynamicTest1() {
        // given
        String name = null;

        String genderParam = null;

        Integer minAge = 20;

        Integer maxAge = 25;

        // when

        // 동적 쿼리를 위한 BooleanBuilder 객체 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (name != null) {
            booleanBuilder.and(idol.idolName.eq(name));
        }

        if (genderParam != null) {
            booleanBuilder.and(idol.gender.eq(genderParam));
        }

        if (minAge != null) {
            booleanBuilder.and(idol.age.goe(minAge));
        }

        if (maxAge != null) {
            booleanBuilder.and(idol.age.loe(maxAge));
        }

        List<Idol> idolList = factory
                .selectFrom(idol)
                .where(booleanBuilder)
                .fetch();

        // then
        idolList.forEach(System.out::println);
    }

    @Test
    @DisplayName("동적 쿼리를 queryDsl 전담 클래스에서 처리")
    void dynamicTest2() {
        // given
        SearchDto dto = SearchDto.builder()
                .name("김")
                .minAge(23)
                .build();

        // when
        List<Idol> result = memberRepository.findIdolByCondition(dto);

        // then
        result.forEach(System.out::println);
    }
    
    @Test
    @DisplayName("동적 정렬을 사용한 아이돌 조회")
    void dynamicTest3() {
        // given
        String sortBy = "age"; // 나이, 이름, 그룹명
        boolean ascending = true; // 오름차(true), 내림차(false)
        
        // when
        OrderSpecifier<?> specifier = null;

        // 동적 정렬 조건 생성
        switch (sortBy) {
            case "age":
                specifier = ascending ? idol.age.asc() : idol.age.desc();
                break;
            case "idolName":
                specifier = ascending ? idol.idolName.asc() : idol.idolName.desc();
                break;
            case "groupName":
                specifier = ascending ? idol.group.groupName.asc() : idol.group.groupName.desc();
                break;
        }

        List<Idol> idolList = factory
                .selectFrom(idol)
                .orderBy(specifier)
                .fetch();

        // then
        idolList.forEach(System.out::println);
    }


}











