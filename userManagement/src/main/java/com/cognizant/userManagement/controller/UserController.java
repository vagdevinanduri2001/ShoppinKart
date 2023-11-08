package com.cognizant.userManagement.controller;

import com.cognizant.userManagement.dao.UserDao;
import com.cognizant.userManagement.entity.Role;
import com.cognizant.userManagement.entity.User;
import com.cognizant.userManagement.exception.UserAlreadyExistsException;
import com.cognizant.userManagement.service.JwtService;
import com.cognizant.userManagement.service.UserService;
import com.cognizant.userManagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.BindException;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDao userDao;


    @PostMapping({"/signUp"})
    public ResponseEntity<?> signUp(@Valid @RequestBody User user, BindingResult bindingResult)
            throws Exception{
        if(bindingResult.hasErrors()){
            throw new BindException();
        }try{
        User createdUser = userService.signUp(user);

        if(createdUser!=null) {

            return new ResponseEntity<>("User created Successfully!!! Now, you can login",
                    HttpStatus.CREATED);
        }
        }catch(UserAlreadyExistsException e){
            return new ResponseEntity<>("User already EXISTS!!!",HttpStatus.NOT_ACCEPTABLE);
        }
        return null;
    }

    @PostMapping("/updateUser")
    //@PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token,
                                        @Valid @RequestBody User user) {
        //userService.hasUserPermission(token);

        Role role = new Role("admin");
        String username = jwtUtil.getUserNameFromToken(token.substring(7));

        Optional<User> tokenUser = userDao.findById(username);


        User checkUserIdExists = userService.getUserDetail(user.getUserId());
        if(checkUserIdExists==null || userService.updateUser(token,user)==null){
            return new ResponseEntity<>("User Id does not Exists to update",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        if (username.equals(user.getUserId()) || jwtService.getAuthorities(tokenUser.get()).contains(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))){
            userService.updateUser(token, user);
            return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Unsuccessful",HttpStatus.NOT_ACCEPTABLE);

    }

    @PostMapping("/hasAdminPermission")
    public boolean hasAdminPermission(@RequestBody User user){
        Role role = new Role("admin");
        if(jwtService.getAuthorities(user).contains(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())))
            return true;
        return false;
    }

    @GetMapping("/getUser/{userId}")
    public User getUser(@PathVariable String userId){
        Optional<User> user =  userDao.findById(userId);
        return user.get();
    }

    @GetMapping("/getUsernameFromToken")
    public String getUsernameFromToken(
            @RequestHeader("Authorization") String token){
        return jwtUtil.getUserNameFromToken(token.substring(7));
    }

    @PostMapping("/updateUserRole/{userId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateUserRole(@RequestHeader("Authorization") String token,
                                         @PathVariable String userId, @RequestBody List<String> role){
        //userService.hasAdminPermission(token);
        User checkUserIdExists = userService.getUserDetail(userId);
        if(checkUserIdExists==null){
            return new ResponseEntity<>("User Id does not Exists to update the role",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        userService.updateUserRole(token,userId,role);
        return new ResponseEntity<>("User roles updated successfully!",HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    @PreAuthorize("hasRole('admin')")
        public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token,
        @PathVariable String userId){

        //userService.hasAdminPermission(token);
        Role role = new Role("admin");
        User checkUserIdExists = userService.getUserDetail(userId);
        if(checkUserIdExists==null){
            return new ResponseEntity<>("User Id does not Exists to delete",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        else if (jwtService.getAuthorities(checkUserIdExists)
                .contains(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))){
            return new ResponseEntity<>("You are not allowed to delete admins...Sorry",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        userService.deleteUser(userId);

        return new ResponseEntity<>("User deleted",HttpStatus.OK);
    }

}