package com.orchasp.app.induslockbox.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.GST;
import com.orchasp.app.induslockbox.repository.CompanyRepository;
import com.orchasp.app.induslockbox.repository.GSTRepository;

@Service
public class GSTService {

	@Autowired
	private GSTRepository GSTRepository;
	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private PasswordService passwordService;

	public List<GST> findAll() {
		return GSTRepository.findAll();
	}

	public Optional<GST> findById(Long id) {
		return GSTRepository.findById(id);
	}

	public GST updateGST(Long id, GST updatedGST) {
		Optional<GST> existingGSTOpt = GSTRepository.findById(id);
		if (existingGSTOpt.isPresent()) {
			GST existingGST = existingGSTOpt.get();

			// Update basic fields

			existingGST.setGstNumber(updatedGST.getGstNumber());
			existingGST.setUserid(updatedGST.getUserid());
			if(!updatedGST.getPassword().equals(existingGST.getPassword()))
			existingGST.setPassword(passwordService.encrypt(updatedGST.getPassword()));
			existingGST.setAddress(updatedGST.getAddress());
			existingGST.setEmail(updatedGST.getEmail());
			existingGST.setMobileno(updatedGST.getMobileno());
			existingGST.setState(updatedGST.getState());
			
			existingGST.setActive(updatedGST.isActive());
			existingGST.setCreatedBy(updatedGST.getCreatedBy());
			existingGST.setCreatedDate(updatedGST.getCreatedDate());
			existingGST.setUpdatedBy(updatedGST.getUpdatedBy());
			existingGST.setUpdatedDate(LocalDateTime.now());

			// Update or save company
			Company updatedCompany = updatedGST.getCompany();
			if (updatedCompany != null) {
				Company existingCompany = existingGST.getCompany();
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
					existingGST.setCompany(updatedCompany);
				}
			}

			return GSTRepository.save(existingGST);
		} else {
			throw new RuntimeException("GST not found with id " + id);
		}
	}

	public GST save(GST GST,String createdBy, Long companyid) {

//		Long companyId = GST.getCompany().getCompanyid();
        Optional<Company> company = companyRepository.findById(companyid);
        Company c=company.get();
        GST.setCompany(c);
        GST.setPassword(passwordService.encrypt(GST.getPassword()));
        GST.setCreatedDate(LocalDateTime.now());
		GST.setActive(true);	
		GST.setCreatedBy(createdBy);
		
		return GSTRepository.save(GST);
	}

	public void deleteById(Long id) {
		Optional<GST> gstop = GSTRepository.findById(id);
		if (gstop.isPresent()) {
			GST gst = gstop.get();
			gst.setActive(false);
			GSTRepository.save(gst);
		}
		// GSTRepository.deleteById(id);
	}

	public void activateById(Long id) {
		Optional<GST> GSTOpt = GSTRepository.findById(id);
		if (GSTOpt.isPresent()) {
			GST gst = GSTOpt.get();
			gst.setActive(true);
			GSTRepository.save(gst);
		}
	}

	public List<GST> findByCompany_id(Long company_id) {
		return GSTRepository.findByCompany_id(company_id);
	}

	public String GetDecryptPassword(Long id) {
		Optional<GST> gst = GSTRepository.findById(id);
		if (gst.isPresent()) {
			GST g = gst.get();
			return passwordService.decrypt(g.getPassword());
		}
		return null;
	}

}