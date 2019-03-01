package com.hyan.electionservice.excepction;

import com.hyan.electionservice.excepction.config.HttpException;
import org.springframework.http.HttpStatus;

public class ElectionExpirationSessionException extends HttpException {

    public ElectionExpirationSessionException (){
        super("Sssão da Eleição/Pauta de votação ENCERRADA.");
    }

    @Override
    public HttpStatus getHttpStatus(){
        return  HttpStatus.FORBIDDEN;
    }
}
