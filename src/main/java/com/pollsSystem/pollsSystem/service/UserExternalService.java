package com.pollsSystem.pollsSystem.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "UserExternalService",
        url = "${userExternalService.url}"
)
public interface UserExternalService {
    @GetMapping("/user/userName/{userId}")
    String getUserNameById(@PathVariable Long userId);
}
