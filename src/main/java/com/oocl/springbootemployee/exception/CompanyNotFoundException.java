package com.oocl.springbootemployee.exception;

public class CompanyNotFoundException extends RuntimeException {
    private static final String COMPANY_NOT_FOUND = "Company Not Found";

    public CompanyNotFoundException() {
        super(COMPANY_NOT_FOUND);
    }
}
