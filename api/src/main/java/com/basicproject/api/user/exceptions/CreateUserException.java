package com.basicproject.api.user.exceptions;

public class CreateUserException extends RuntimeException{
    public CreateUserException(){
        super("Kayıt İşlemi Sırasında Hata Oluştu(Return Null Object)");
    }
}
