package com.orchasp.app.induslockbox.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.ESI;
import com.orchasp.app.induslockbox.repository.CompanyRepository;
import com.orchasp.app.induslockbox.repository.ESIRepository;

@Service
public class ESIService {

    @Autowired
    private ESIRepository ESIRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordService passwordService; // Injecting PasswordService

    public List<ESI> findAll() {
        return ESIRepository.findAll();
    }

    public List<ESI> findByCompany_id(Long company_id) {
        return ESIRepository.findByCompany_id(company_id);
    }

    public Optional<ESI> findById(Long id) {
        return ESIRepository.findById(id);
    }

    public ESI updateESI(Long id, ESI updatedESI) {
        Optional<ESI> existingESIOpt = ESIRepository.findById(id);
        if (existingESIOpt.isPresent()) {
            ESI existingESI = existingESIOpt.get();

            // Update basic fields
            existingESI.setEmployerCodeNo(updatedESI.getEmployerCodeNo());
            existingESI.setEmployerName(updatedESI.getEmployerName());
            existingESI.setEmailId(updatedESI.getEmailId());
            existingESI.setMobileNo(updatedESI.getMobileNo());
            existingESI.setUserid(updatedESI.getUserid());
            if(!updatedESI.getPassword().equals(existingESI.getPassword()))
            existingESI.setPassword(passwordService.encrypt(updatedESI.getPassword()));
            existingESI.setSignatory(updatedESI.getSignatory());
            existingESI.setRo(updatedESI.getRo());
            existingESI.setLin(updatedESI.getLin());
            existingESI.setCreatedBy(updatedESI.getCreatedBy());
            existingESI.setCreatedDate(updatedESI.getCreatedDate());
            existingESI.setUpdatedBy(updatedESI.getUpdatedBy());
            existingESI.setUpdatedDate(LocalDateTime.now());
            existingESI.setActive(updatedESI.isActive());

            // Update or save company
            Company updatedCompany = updatedESI.getCompany();
            if (updatedCompany != null) {
                Company existingCompany = existingESI.getCompany();
                if (existingCompany != null) {
                	 existingCompany.setCompanyCode(updatedCompany.getCompanyCode());
                     existingCompany.setCin(updatedCompany.getCin());
                     existingCompany.setCompanyname(updatedCompany.getCompanyname());
                     existingCompany.setDateOfIncorporation(updatedCompany.getDateOfIncorporation());
                     existingCompany.setRegisterNo(updatedCompany.getRegisterNo());
                     existingCompany.setPhoneNo(updatedCompany.getPhoneNo());
                     existingCompany.setEmail(updatedCompany.getEmail());
                     existingCompany.setAddress(updatedCompany.getAddress());
                     existingCompany.setWebsite(updatedCompany.getWebsite());
                     existingCompany.setTelephoneNo(updatedCompany.getTelephoneNo());
                     existingCompany.setFaxNo(updatedCompany.getFaxNo());
                     existingCompany.setCity(updatedCompany.getCity());
                     existingCompany.setState(updatedCompany.getState());
                     existingCompany.setPincode(updatedCompany.getPincode());
                     existingCompany.setUpdatedBy(updatedCompany.getUpdatedBy());
                     existingCompany.setUpdatedDate(LocalDateTime.now());
                     existingCompany.setActive(updatedCompany.isActive());

                    // Save the updated existing company
                    companyRepository.save(existingCompany);
                } else {
                    companyRepository.save(updatedCompany);
                    existingESI.setCompany(updatedCompany);
                }
            }

            return ESIRepository.save(existingESI);
        } else {
            throw new RuntimeException("ESI not found with id " + id);
        }
    }

    public ESI save(ESI ESI) {
        Long companyId = ESI.getCompany().getCompanyid();
        Optional<Company> company = companyRepository.findById(companyId);
        company.ifPresent(ESI::setCompany);

        ESI.setCreatedDate(LocalDateTime.now());
        ESI.setActive(true);

        return ESIRepository.save(ESI);
    }

    public void deleteById(Long id) {
        Optional<ESI> ESIOpt = ESIRepository.findById(id);
        ESIOpt.ifPresent(esi -> {
            esi.setActive(false);
            ESIRepository.save(esi);
        });
    }

    public void activateById(Long id) {
        Optional<ESI> ESIOpt = ESIRepository.findById(id);
        ESIOpt.ifPresent(esi -> {
            esi.setActive(true);
            ESIRepository.save(esi);
        });
    }

    public String GetDecryptPassword(Long id) {
        Optional<ESI> ESIOpt = ESIRepository.findById(id);
        if (ESIOpt.isPresent()) {
            ESI esi = ESIOpt.get();
            return passwordService.decrypt(esi.getPassword());
        } else {
            throw new RuntimeException("ESI not found with id " + id);
        }
    }
}
