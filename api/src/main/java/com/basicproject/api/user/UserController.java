package com.basicproject.api.user;

import com.basicproject.api.errors.ApiError;
import com.basicproject.api.user.exceptions.CreateUserException;
import com.basicproject.api.user.exceptions.GeneralException;
import com.basicproject.api.user.userdto.CreateUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1")
@CrossOrigin
public class UserController {

    UserService _userService;

    public UserController(UserService userService){
        _userService = userService;
    }

    @PostMapping(path = "/users")
    ResponseEntity<?> createUser(@Valid  @RequestBody CreateUser createUser){
        /*if(_userService.save(createUser)==null){
            throw new GeneralException("Kayıt İşlemi Sırasında Hata Oluştu(Return Null Object)");
        }*/
        return ResponseEntity.ok().body(_userService.save(createUser));
    }

    @PatchMapping(path = "/users/activation/{activationToken}")
    ResponseEntity<?> activateUser (@PathVariable String activationToken){
        var hasUser = _userService.activate(activationToken);
        if(hasUser){
            return ResponseEntity.ok().body(true);
        }else{
            return ResponseEntity.ok().body(false);
        }
    }

    @ExceptionHandler(RuntimeException.class)
    ApiError runtimeExceptionHandler(RuntimeException exception,HttpServletRequest request){
        ApiError apiError = new ApiError();
        apiError.setMessage("RUNTIME EXCEPTION HATASI");
        apiError.setPath(request.getRequestURI());
        apiError.setStatusCode(400);
        return apiError;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
