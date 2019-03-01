package com.hyan.electionservice.excepction;

import com.hyan.electionservice.excepction.config.HttpException;
import org.springframework.http.HttpStatus;

public class InvalidTaxIdExcption extends HttpException {

    public InvalidTaxIdExcption(){
        super("CPF inválido");
    }

    @Override
    public HttpStatus getHttpStatus(){
        return  HttpStatus.BAD_REQUEST;
    }
}
