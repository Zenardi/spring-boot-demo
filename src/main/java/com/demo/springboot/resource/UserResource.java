package com.demo.springboot.resource;

import com.demo.springboot.model.User;
import com.demo.springboot.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;


@RestController
@RequestMapping(
        path="/api/v1/users/"
)
public class UserResource {

    private UserService userService;

    @Autowired
    public UserResource(UserService usrService) {
        this.userService = usrService;
    }

    @RequestMapping(
            method= RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<User> fetchUsers(@QueryParam("gender") String gender){


        return userService.getAllUsers(Optional.ofNullable(gender));
    }

    @RequestMapping(
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path =  "{userUid}"
    )
    public ResponseEntity<?> fetchUser(@PathVariable("userUid") UUID userUid){
        return userService.getUser(userUid).<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("user " + userUid + " was not found.")));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> insertNewUser(@RequestBody User user){
        int result = userService.insertUser(user);
        if(result == 1){
            return ResponseEntity.ok().build();

        }
        return  ResponseEntity.badRequest().build();
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> updateUser(@RequestBody User user){
        int result = userService.updateUser(user);
        if(result == 1){
            return ResponseEntity.ok().build();

        }
        return  ResponseEntity.badRequest().build();
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{userUid}"
    )
    public ResponseEntity<Integer> deleteUser(@PathVariable("userUid") UUID userUid){
        int result = userService.removeuser(userUid);
        if(result == 1){
            return ResponseEntity.ok().build();

        }
        return  ResponseEntity.badRequest().build();
    }

    class ErrorMessage{
        String errorMessage;

        public ErrorMessage(String msg) {
            this.errorMessage = msg;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
