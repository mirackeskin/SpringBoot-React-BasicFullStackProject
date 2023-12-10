package com.basicproject.api.user;

import com.basicproject.api.auth.token.TokenService;
import com.basicproject.api.errors.ApiError;
import com.basicproject.api.user.exceptions.AuthorizationException;
import com.basicproject.api.user.exceptions.CreateUserException;
import com.basicproject.api.user.exceptions.GeneralException;
import com.basicproject.api.user.userdto.CreateUser;
import com.basicproject.api.user.userdto.ImageDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1")
@CrossOrigin
public class UserController {

    UserService _userService;
    TokenService _tokenService;

    public UserController(UserService userService,TokenService tokenService){

        _userService = userService;
        _tokenService = tokenService;
    }

    @PostMapping(path = "/users")
    ResponseEntity<?> createUser(@Valid  @RequestBody CreateUser createUser){
        /*if(_userService.save(createUser)==null){
            throw new GeneralException("Kayıt İşlemi Sırasında Hata Oluştu(Return Null Object)");
        }*/
        return ResponseEntity.ok().body(_userService.save(createUser));
    }

    @GetMapping(path = "/users/{id}")
    ResponseEntity<?> getAllUsers(@PathVariable int id,@RequestHeader(name = "Authorization",required = false) String header){
        if(_tokenService.verifyToken(header)==null || _tokenService.verifyToken(header).getUserId()!=id){
            throw new AuthorizationException("Authorization Error(Invalid Token Problem)");
        }
        return ResponseEntity.ok().body(_userService.getAllUsers(id));
    }

    @GetMapping(path = "/user/{id}")
    ResponseEntity<User> getUser (@PathVariable int id,@RequestHeader(name = "Authorization",required = false) String header){
        if(_tokenService.verifyToken(header)==null || _tokenService.verifyToken(header).getUserId()!=id){
            throw new AuthorizationException("Authorization Error(Invalid Token Problem)");
        }
        return ResponseEntity.ok().body(_userService.getUser(id));
    }

    @PostMapping(path = "/user/fileUpload/{id}")
    ResponseEntity<?> fileUpload(@PathVariable int id,@RequestParam MultipartFile file) throws IOException {
        byte [] content = file.getInputStream().readAllBytes();
        String byteToString = Base64.getEncoder().encodeToString(content);
        return ResponseEntity.ok().body(_userService.profileImageUpload(id,byteToString));
    }

    @DeleteMapping(path = "/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable int id,@RequestHeader(name = "Authorization",required = false) String header){
        if(_tokenService.verifyToken(header)==null){
            throw new AuthorizationException("Authorization Error(Invalid Token Problem)");
        }
        return ResponseEntity.ok().body(_userService.deleteUser(id));

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

    /*
    @GetMapping("/test")
    ResponseEntity<?> test(HttpServletRequest request,@CookieValue(name = "token",defaultValue = "aaaaaa") String myCookie){
        var cookieValues = request.getCookies();
        return ResponseEntity.ok().body(cookieValues);
    }*/

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
