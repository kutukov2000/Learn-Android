package com.example.firstapp.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class JWTInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFkbWluQGdtYWlsLmNvbSIsIm5hbWUiOiLQrtGF0LjQvCDQmtCw0L_QvtGCIiwiaW1hZ2UiOiIiLCJyb2xlcyI6IkFkbWluIiwiZXhwIjoxNzE1MTc3MTM5fQ.Emt_TNsWUf7UyVFGGxlGCeyRNiLznlPel6hPzOgtRZM";
        Request.Builder builder = chain.request().newBuilder();
        if (token != null && !token.isEmpty()) {
            builder.header("Authorization", "Bearer " + token);
        }
        return chain.proceed(builder.build());
    }
}