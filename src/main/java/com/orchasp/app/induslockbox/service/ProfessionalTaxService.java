package com.orchasp.app.induslockbox.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.ProfessionalTax;
import com.orchasp.app.induslockbox.repository.CompanyRepository;
import com.orchasp.app.induslockbox.repository.ProfessionalTaxRepository;

@Service
public class ProfessionalTaxService {

	@Autowired
	private ProfessionalTaxRepository ProfessionalTaxRepository;

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private PasswordService passwordService;

	public List<ProfessionalTax> findAll() {
		return ProfessionalTaxRepository.findAll();
	}

	public List<ProfessionalTax> findByCompany_id(Long company_id) {
		return ProfessionalTaxRepository.findByCompany_id(company_id);
	}

	public Optional<ProfessionalTax> findById(Long id) {
		return ProfessionalTaxRepository.findById(id);
	}

	public ProfessionalTax updateProfessionalTax(Long id, ProfessionalTax updatedProfessionalTax) {
		Optional<ProfessionalTax> existingProfessionalTaxOpt = ProfessionalTaxRepository.findById(id);
		if (existingProfessionalTaxOpt.isPresent()) {
			ProfessionalTax existingProfessionalTax = existingProfessionalTaxOpt.get();

			// Update basic fields
			existingProfessionalTax.setPtinCom(updatedProfessionalTax.getPtinCom());
			existingProfessionalTax.setPtinSal(updatedProfessionalTax.getPtinSal());
			existingProfessionalTax.setTaxDivision(updatedProfessionalTax.getTaxDivision());
			existingProfessionalTax.setEmail(updatedProfessionalTax.getEmail());
			existingProfessionalTax.setTaxCircle(updatedProfessionalTax.getTaxCircle());
			existingProfessionalTax.setMobileNo(updatedProfessionalTax.getMobileNo());
			existingProfessionalTax.setDateOfEnrollment(updatedProfessionalTax.getDateOfEnrollment());
			existingProfessionalTax.setUserid(updatedProfessionalTax.getUserid());
			if(!updatedProfessionalTax.getPassword().equals(existingProfessionalTax.getPassword()))
			existingProfessionalTax.setPassword(passwordService.encrypt(updatedProfessionalTax.getPassword()));

			existingProfessionalTax.setCreatedBy(updatedProfessionalTax.getCreatedBy());
			existingProfessionalTax.setCreatedDate(updatedProfessionalTax.getCreatedDate());
			existingProfessionalTax.setUpdatedBy(updatedProfessionalTax.getUpdatedBy());
			existingProfessionalTax.setUpdatedDate(LocalDateTime.now());
			existingProfessionalTax.setActive(updatedProfessionalTax.isActive());
			// Update or save company
			Company updatedCompany = updatedProfessionalTax.getCompany();
			if (updatedCompany != null) {
				Company existingCompany = existingProfessionalTax.getCompany();
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
					existingProfessionalTax.setCompany(updatedCompany);
				}
			}

			return ProfessionalTaxRepository.save(existingProfessionalTax);
		} else {
			throw new RuntimeException("Bank not found with id " + id);
		}
	}

	public ProfessionalTax save(ProfessionalTax ProfessionalTax) {
		Long companyId = ProfessionalTax.getCompany().getCompanyid();
        Optional<Company> company = companyRepository.findById(companyId);
        Company c=company.get();
        ProfessionalTax.setPassword(passwordService.encrypt(ProfessionalTax.getPassword()));
        ProfessionalTax.setCompany(c);
        ProfessionalTax.setActive(true);
        ProfessionalTax.setCreatedDate(LocalDateTime.now());
		
		return ProfessionalTaxRepository.save(ProfessionalTax);
	}

	public void deleteById(Long id) {
		Optional<ProfessionalTax> bankOpt = ProfessionalTaxRepository.findById(id);
		if (bankOpt.isPresent()) {
			ProfessionalTax b = bankOpt.get();
			b.setActive(false);
			ProfessionalTaxRepository.save(b);
		}
//        bankRepository.deleteById(id);
	}

	public void activateById(Long id) {
		Optional<ProfessionalTax> bankOpt = ProfessionalTaxRepository.findById(id);
		if (bankOpt.isPresent()) {
			ProfessionalTax bank = bankOpt.get();
			bank.setActive(true);
			ProfessionalTaxRepository.save(bank);
		}
	}
	
	public String GetDecryptPassword(Long id) {
		Optional<ProfessionalTax> gst=ProfessionalTaxRepository.findById(id);
		if(gst.isPresent()) {
			ProfessionalTax g=gst.get();
			return passwordService.decrypt(g.getPassword());
		}
		return null;
	}
	
}
