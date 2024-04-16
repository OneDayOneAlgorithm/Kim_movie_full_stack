package com.example.kim_movie.controller.request;

import lombok.Data;

public class MemberRequest {
    @Data
    public static class Create {
        private String email;
        private String password;
        private String username;
    }

    @Data
    public static class Update {
        private String password;
        private String username;
    }

    @Data
    public static class Login {
        private String email;
        private String password;
    }
}
