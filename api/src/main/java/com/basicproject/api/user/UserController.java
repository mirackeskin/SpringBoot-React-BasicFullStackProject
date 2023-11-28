package com.basicproject.api.user;

import com.basicproject.api.errors.ApiError;
import com.basicproject.api.user.userdto.CreateUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1")
public class UserController {

    @PostMapping(path = "/users")
    ResponseEntity<?> createUser(@Valid  @RequestBody CreateUser createUser){
        return ResponseEntity.ok().body(createUser);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiError exceptionHandler(MethodArgumentNotValidException exception, HttpServletRequest request){
        ApiError apiError = new ApiError();
        apiError.setMessage("Hatalı Kullanıcı Bilgileri");
        apiError.setPath(request.getRequestURI());
        apiError.setStatusCode(400);
        Map<String,String> validationErrors = new HashMap<>();
        for(var error : exception.getFieldErrors()){
            validationErrors.put(error.getField(),error.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return apiError;
    }

}
