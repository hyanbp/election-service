package com.hyan.electionservice.excepction;

import com.hyan.electionservice.excepction.config.HttpException;
import org.springframework.http.HttpStatus;

public class AssociateAlreadyVotedExpcetion extends HttpException {

    public AssociateAlreadyVotedExpcetion(){
        super("Associado já realizou o voto. Só é permitido um voto por sessão.");
    }

    @Override
    public HttpStatus getHttpStatus(){
        return  HttpStatus.UNAUTHORIZED;
    }
}
