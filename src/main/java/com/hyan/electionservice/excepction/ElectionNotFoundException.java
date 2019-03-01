package com.hyan.electionservice.excepction;

import com.hyan.electionservice.excepction.config.HttpException;
import org.springframework.http.HttpStatus;

public class ElectionNotFoundException extends HttpException {

    public ElectionNotFoundException (){
        super("Eleição/Pauta de votação não encontrada.");
    }

    @Override
    public HttpStatus getHttpStatus(){
        return  HttpStatus.NOT_FOUND;
    }

}
