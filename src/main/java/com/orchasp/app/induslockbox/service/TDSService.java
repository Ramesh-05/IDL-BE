package com.orchasp.app.induslockbox.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.TDS;
import com.orchasp.app.induslockbox.repository.CompanyRepository;
import com.orchasp.app.induslockbox.repository.TDSRepository;

@Service
public class TDSService {
    @Autowired
    private TDSRepository tdsRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordService passwordService;

    public List<TDS> findAll() {
        return tdsRepository.findAll();
    }

    public Optional<TDS> findById(Long id) {
        return tdsRepository.findById(id);
    }

    public TDS updateTDS(Long id, TDS updatedTDS) {
        Optional<TDS> existingTDSOpt = tdsRepository.findById(id);
        if (existingTDSOpt.isPresent()) {
            TDS existingTDS = existingTDSOpt.get();

            // Update basic fields
            existingTDS.setTanNo(updatedTDS.getTanNo());
            existingTDS.setEmail(updatedTDS.getEmail());
            existingTDS.setPhoneNumber(updatedTDS.getPhoneNumber());
            existingTDS.setUserid(updatedTDS.getUserid());
            if(!updatedTDS.getPassword().equals(existingTDS.getPassword()))
            existingTDS.setPassword(passwordService.encrypt(updatedTDS.getPassword()));
            existingTDS.setSurrendered(updatedTDS.getSurrendered());
            existingTDS.setSignatory(updatedTDS.getSignatory());
            existingTDS.setActive(updatedTDS.isActive());
            existingTDS.setCreatedBy(updatedTDS.getCreatedBy());
            existingTDS.setCreatedDate(updatedTDS.getCreatedDate());
            existingTDS.setUpdatedBy(updatedTDS.getUpdatedBy());
            existingTDS.setUpdatedDate(LocalDateTime.now());

            // Update or save company
            Company updatedCompany = updatedTDS.getCompany();
            if (updatedCompany != null) {
                Company existingCompany = existingTDS.getCompany();
                if (existingCompany != null) {
                    // Update existing company fields
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

                    companyRepository.save(existingCompany);
                } else {
                    companyRepository.save(updatedCompany);
                    existingTDS.setCompany(updatedCompany);
                }
            }
            return tdsRepository.save(existingTDS);
        } else {
            throw new RuntimeException("TDS not found with id " + id);
        }
    }

    public TDS save(TDS tds) {
        Long companyId = tds.getCompany().getCompanyid();
        Optional<Company> companyOpt = companyRepository.findById(companyId);
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            tds.setCompany(company);
            tds.setActive(true);
            tds.setPassword(passwordService.encrypt(tds.getPassword()));
            tds.setCreatedDate(LocalDateTime.now());

            return tdsRepository.save(tds);
        } else {
            throw new RuntimeException("Company not found with id " + companyId);
        }
    }

    public List<TDS> findByCompany_id(Long company_id) {
        return tdsRepository.findByCompany_id(company_id);
    }

    public void deleteById(Long id) {
        Optional<TDS> tdsOpt = tdsRepository.findById(id);
        if (tdsOpt.isPresent()) {
            TDS tds = tdsOpt.get();
            tds.setActive(false);
            tdsRepository.save(tds);
        } else {
            throw new RuntimeException("TDS not found with id " + id);
        }
    }

    public void activateById(Long id) {
        Optional<TDS> tdsOpt = tdsRepository.findById(id);
        if (tdsOpt.isPresent()) {
            TDS tds = tdsOpt.get();
            tds.setActive(true);
            tdsRepository.save(tds);
        } else {
            throw new RuntimeException("TDS not found with id " + id);
        }
    }

    public String getDecryptPassword(Long id) {
        Optional<TDS> tdsOpt = tdsRepository.findById(id);
        if (tdsOpt.isPresent()) {
            TDS tds = tdsOpt.get();
            return passwordService.decrypt(tds.getPassword());
        } else {
            throw new RuntimeException("TDS not found with id " + id);
        }
    }
}
