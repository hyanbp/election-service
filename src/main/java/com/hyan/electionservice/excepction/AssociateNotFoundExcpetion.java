package com.hyan.electionservice.excepction;

import com.hyan.electionservice.excepction.config.HttpException;
import org.springframework.http.HttpStatus;

public class AssociateNotFoundExcpetion extends HttpException {

    public AssociateNotFoundExcpetion(){
        super("Associado não encontrado");
    }

    @Override
    public HttpStatus getHttpStatus(){
        return  HttpStatus.NOT_FOUND;
    }


}
