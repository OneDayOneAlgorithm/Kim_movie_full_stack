package com.example.kim_movie.controller.response;

import com.example.kim_movie.domain.model.Member;
import lombok.Builder;
import lombok.Data;

public class MemberResponse {
    @Data
    @Builder
    public static class Detail {
        private Long id;
        private String email;
        private String password;
        private String username;
        private String dateJoined;
        private Boolean isStaff;

        public static Detail of(Member member){
            return Detail.builder()
                    .id(member.getMemberId())
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .username(member.getUsername())
                    .dateJoined(member.getDateJoined())
                    .isStaff(member.getIsStaff())
                    .build();
        }
    }
}
