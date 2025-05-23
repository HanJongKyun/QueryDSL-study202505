package com.playdata.study.dto;

import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {

    private String name;
    private String gender;
    private Integer minAge;
    private Integer maxAge;

}