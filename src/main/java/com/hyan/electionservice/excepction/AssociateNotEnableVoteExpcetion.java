package com.hyan.electionservice.excepction;

import com.hyan.electionservice.excepction.config.HttpException;
import org.springframework.http.HttpStatus;

public class AssociateNotEnableVoteExpcetion extends HttpException {

    public AssociateNotEnableVoteExpcetion(){
        super("Associado não habilitado a votar");
    }

    @Override
    public HttpStatus getHttpStatus(){
        return  HttpStatus.UNAUTHORIZED;
    }

}
