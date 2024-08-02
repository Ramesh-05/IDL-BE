package com.orchasp.app.induslockbox.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.EPF;
import com.orchasp.app.induslockbox.repository.CompanyRepository;
import com.orchasp.app.induslockbox.repository.EPFRepository;

@Service
public class EPFService {

    @Autowired
    private EPFRepository epfRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordService passwordService;

    public List<EPF> findAll() {
        return epfRepository.findAll();
    }

    public Optional<EPF> findById(Long id) {
        return epfRepository.findById(id);
    }

    public EPF updateEPF(Long id, EPF updatedEPF) {
        Optional<EPF> existingEPFOpt = epfRepository.findById(id);
        if (existingEPFOpt.isPresent()) {
            EPF existingEPF = existingEPFOpt.get();
            existingEPF.setEstid(updatedEPF.getEstid());
            existingEPF.setLin(updatedEPF.getLin());
            existingEPF.setNiccode(updatedEPF.getNiccode());
            existingEPF.setPanno(updatedEPF.getPanno());
            existingEPF.setEmailId(updatedEPF.getEmailId());
            existingEPF.setMobileNo(updatedEPF.getMobileNo());
            existingEPF.setUserid(updatedEPF.getUserid());
            if(!updatedEPF.getPassword().equals(existingEPF.getPassword()))
            existingEPF.setPassword(passwordService.encrypt(updatedEPF.getPassword()));
            existingEPF.setPfOfficeAddress(updatedEPF.getPfOfficeAddress());
            existingEPF.setSignatory(updatedEPF.getSignatory());
            existingEPF.setActive(updatedEPF.isActive());
            existingEPF.setCreatedBy(updatedEPF.getCreatedBy());
            existingEPF.setCreatedDate(updatedEPF.getCreatedDate());
            existingEPF.setUpdatedBy(updatedEPF.getUpdatedBy());
            existingEPF.setUpdatedDate(LocalDateTime.now());

            // Update or save company
            Company updatedCompany = updatedEPF.getCompany();
            if (updatedCompany != null) {
                Company existingCompany = existingEPF.getCompany();
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
                    existingEPF.setCompany(updatedCompany);
                }
            }

            return epfRepository.save(existingEPF);
        } else {
            throw new RuntimeException("EPF not found with id " + id);
        }
    }


 

    public EPF save(EPF epf) {
        Long companyId = epf.getCompany().getCompanyid();
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isPresent()) {
            Company c = company.get();
            epf.setCompany(c);
            epf.setActive(true);
            epf.setCreatedDate(LocalDateTime.now());
            epf.setPassword(passwordService.encrypt(epf.getPassword()));

            return epfRepository.save(epf);
        } else {
            throw new RuntimeException("bank not found with id " + companyId);
        }
    }

    public void deleteById(Long id) {
        Optional<EPF> epfOpt = epfRepository.findById(id);
        if (epfOpt.isPresent()) {
            EPF epf = epfOpt.get();
            epf.setActive(false);
            epfRepository.save(epf);
        } else {
            throw new RuntimeException("EPF not found with id " + id);
        }
    }

    public List<EPF> findByCompany_id(Long company_id) {
        return epfRepository.findByCompany_id(company_id);
    }

    public void activateById(Long id) {
        Optional<EPF> epfOpt = epfRepository.findById(id);
        if (epfOpt.isPresent()) {
            EPF epf = epfOpt.get();
            epf.setActive(true);
            epfRepository.save(epf);
        } else {
            throw new RuntimeException("EPF not found with id " + id);
        }
    }
        
        public String GetDecryptPassword(Long id) {
            Optional<EPF> epfOpt = epfRepository.findById(id);
            if (epfOpt.isPresent()) {
                EPF epf = epfOpt.get();
                return passwordService.decrypt(epf.getPassword());
            } else {
                throw new RuntimeException("EPF not found with id " + id);
            }
        
    }
    
}
