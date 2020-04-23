package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ResponseBaseConfiguration
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ResponseBaseConfiguration<T> {
    private boolean status;
    private String code;
    private String message;
    private T data;

    public static ResponseBaseConfiguration error(String code, String message) {
        return new ResponseBaseConfiguration<>(false, code, message, null);
    }

    public static ResponseBaseConfiguration ok() {
        return new ResponseBaseConfiguration<>(true, "200", "success", null);
    }

    public static <I> ResponseBaseConfiguration<I> ok(I body) {
        return new ResponseBaseConfiguration<I>(true, "200", "success", body);
    }

    public static ResponseBaseConfiguration created() {
        return new ResponseBaseConfiguration<>(true, "201", "created", null);
    }

    public static ResponseBaseConfiguration created(String uri) {
        ResponseBaseConfiguration<Map> baseResponse = new ResponseBaseConfiguration<>();
        baseResponse.setStatus(true);
        baseResponse.setCode("201");
        baseResponse.setMessage("created");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("uri", uri);
        baseResponse.setData(map);
        return baseResponse;
    }
}
