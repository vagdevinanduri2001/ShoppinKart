package com.cognizant.productsService.feignclient;

import com.cognizant.productsService.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user", url = "${USER:http://localhost:8080}")
public interface UserFeign {

    @GetMapping("/getUser/{userId}")
    public User getUser(@RequestHeader("Authorization")String token,@PathVariable String userId);

    @GetMapping("/getUsernameFromToken")
    public String getUsernameFromToken(@RequestHeader("Authorization")String token);

    @PostMapping("/hasAdminPermission")
    public boolean hasAdminPermission(@RequestHeader("Authorization")String token,@RequestBody User user);

}
