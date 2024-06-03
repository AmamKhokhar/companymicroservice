package com.learner.companyms.company.serviceimpl;


import com.learner.companyms.company.Company;
import com.learner.companyms.company.CompanyRepository;
import com.learner.companyms.company.CompanyService;
import com.learner.companyms.company.clients.ReviewClient;
import com.learner.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final ReviewClient reviewClient;
    CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public String createCompany(Company company) {
         companyRepository.save(company);
        return null;
    }

    @Override
    public boolean updateById(Long id, Company updatedCompany) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()){
            Company company = companyOptional.get();
            company.setName(updatedCompany.getName());
            company.setDescription(updatedCompany.getDescription());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        if (companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company = companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(()-> new NotFoundException("Company Not Found" + reviewMessage.getCompanyId()));
        Double averageRating  = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }
}
