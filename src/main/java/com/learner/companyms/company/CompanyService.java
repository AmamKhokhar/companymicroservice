package com.learner.companyms.company;

import java.util.List;

public interface CompanyService {

    List<Company> findAll();

    Company findById(Long id);
    String createCompany(Company company);

    boolean updateById(Long id , Company updatedCompany);
    boolean deleteById(Long id);
}
