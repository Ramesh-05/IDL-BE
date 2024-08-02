package com.orchasp.app.induslockbox.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.EPF;
import com.orchasp.app.induslockbox.entity.IncomeTax;
import com.orchasp.app.induslockbox.repository.CompanyRepository;
import com.orchasp.app.induslockbox.repository.IncomeTaxRepository;

@Service
public class IncomeTaxService {

    @Autowired
    private IncomeTaxRepository IncomeTaxRepository;
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private PasswordService passwordService;

    public List<IncomeTax> findAll() {
        return IncomeTaxRepository.findAll();
    }

    public Optional<IncomeTax> findById(Long id) {
        return IncomeTaxRepository.findById(id);
    }
    
    public IncomeTax updateIncomeTax(Long id, IncomeTax updatedIncomeTax) {
        Optional<IncomeTax> existingIncomeTaxOpt = IncomeTaxRepository.findById(id);
        if (existingIncomeTaxOpt.isPresent()) {
            IncomeTax existingIncomeTax = existingIncomeTaxOpt.get();

            // Update basic fields
            existingIncomeTax.setPanNumber(updatedIncomeTax.getPanNumber());
            existingIncomeTax.setName(updatedIncomeTax.getName());
            existingIncomeTax.setPanIssuedDate(updatedIncomeTax.getPanIssuedDate());
            existingIncomeTax.setIncorporationDate(updatedIncomeTax.getIncorporationDate());
            existingIncomeTax.setPrimaryEmail(updatedIncomeTax.getPrimaryEmail());
            existingIncomeTax.setSecondaryEmail(updatedIncomeTax.getSecondaryEmail());
            existingIncomeTax.setPrimaryMobile(updatedIncomeTax.getPrimaryMobile());
            existingIncomeTax.setSecondaryMobile(updatedIncomeTax.getSecondaryMobile());
            existingIncomeTax.setUserid(updatedIncomeTax.getUserid());
            if(!updatedIncomeTax.getPassword().equals(existingIncomeTax.getPassword()))
            existingIncomeTax.setPassword(passwordService.encrypt( updatedIncomeTax.getPassword()));
           
            existingIncomeTax.setActive(updatedIncomeTax.isActive());
            existingIncomeTax.setCreatedBy(updatedIncomeTax.getCreatedBy());
            existingIncomeTax.setCreatedDate(updatedIncomeTax.getCreatedDate());
            existingIncomeTax.setUpdatedBy(updatedIncomeTax.getUpdatedBy());
            existingIncomeTax.setUpdatedDate(LocalDateTime.now());

            // Update or save company
            Company updatedCompany = updatedIncomeTax.getCompany();
            if (updatedCompany != null) {
                Company existingCompany = existingIncomeTax.getCompany();
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
                    existingIncomeTax.setCompany(updatedCompany);
                }
            }

            return IncomeTaxRepository.save(existingIncomeTax);
        } else {
            throw new RuntimeException("IncomeTax not found with id " + id);
        }
    }

    public IncomeTax save(IncomeTax IncomeTax) {
    	Long companyId = IncomeTax.getCompany().getCompanyid();
        Optional<Company> company = companyRepository.findById(companyId);
        Company c=company.get();
        IncomeTax.setCompany(c);
        IncomeTax.setPassword(passwordService.encrypt(IncomeTax.getPassword()));
        IncomeTax.setActive(true);
       
        IncomeTax.setCreatedDate(LocalDateTime.now());
       
       
        return IncomeTaxRepository.save(IncomeTax);
    }

    public void deleteById(Long id) {
    	Optional<IncomeTax> itop=IncomeTaxRepository.findById(id);
    	if(itop.isPresent()) {
    	IncomeTax it=itop.get();
    	it.setActive(false);
    	IncomeTaxRepository.save(it);
    	}
    	//IncomeTaxRepository.deleteById(id);
    }
    
    public void activateById(Long id) {
		Optional<IncomeTax> IncomeTaxOpt = IncomeTaxRepository.findById(id);
		if (IncomeTaxOpt.isPresent()) {
			IncomeTax it =IncomeTaxOpt.get();
			it.setActive(true);
			IncomeTaxRepository.save(it);
		}
	}
    public List<IncomeTax> findByCompany_id(Long company_id) {
		return IncomeTaxRepository.findByCompany_id(company_id);
	}
    public String GetDecryptPassword(Long id) {
        Optional<IncomeTax> IncomeTaxOpt = IncomeTaxRepository.findById(id);
        if (IncomeTaxOpt.isPresent()) {
           IncomeTax incometax = IncomeTaxOpt.get();
            return passwordService.decrypt(incometax.getPassword());
        } else {
            throw new RuntimeException("IncomeTax not found with id " + id);
        }
    
}


}