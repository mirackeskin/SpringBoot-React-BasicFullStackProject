package com.basicproject.api.auth;

import com.basicproject.api.auth.dto.Credentials;
import com.basicproject.api.errors.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1")
@CrossOrigin
public class AuthController {

    AuthService _authService;
    public AuthController(AuthService authService){
        _authService = authService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody Credentials credentials){
        return ResponseEntity.ok().body(_authService.authenticateUser(credentials));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiError validationExceptionHandler(MethodArgumentNotValidException exception, HttpServletRequest request){
        ApiError apiError = new ApiError();
        apiError.setStatusCode(404);
        apiError.setPath(request.getRequestURI());
        apiError.setMessage("VALİDATİON EXCEPTİON HANDLED");
        Map<String,String> validationErrors = new HashMap<>();

        for(var error : exception.getFieldErrors()){
            validationErrors.put(error.getField(), error.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return apiError;
    }

}
