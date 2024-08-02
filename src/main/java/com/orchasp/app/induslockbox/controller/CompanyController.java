package com.orchasp.app.induslockbox.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orchasp.app.induslockbox.dto.CompanyDTO;
import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.service.CompanyService;

@RestController
@RequestMapping("/api/admin/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@GetMapping("/fetchall")
	public List<Company> findAll() {
		return companyService.findAll();
	}

	@GetMapping("/fetchbyid/{id}")
	public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
		Company company = companyService.findById(id);
		return ResponseEntity.ok(company);
	}

	@PostMapping("/save")
	public ResponseEntity<?> registerCompany(@RequestParam("companyCode") String companyCode,
			@RequestParam("companyname") String companyname, @RequestParam("cin") String cin,
			@RequestParam("registerNo") String registerNo,
			@RequestParam("dateOfIncorporation") String dateOfIncorporation,
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfIncorporation,
			@RequestParam("address") String address, @RequestParam("city") String city,
			@RequestParam("state") String state, @RequestParam("pincode") String pincode,
			@RequestParam("email") String email, @RequestParam("phoneNo") String phoneNo,
			@RequestParam("telephoneNo") String telephoneNo, @RequestParam("faxNo") String faxNo,
			@RequestParam("website") String website, @RequestParam("logoName") MultipartFile logoName,
			@RequestParam("active") Boolean active) throws IOException {

		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setCompanyCode(companyCode);
		companyDTO.setCompanyname(companyname);
		companyDTO.setCin(cin);
		companyDTO.setRegisterNo(registerNo);
		companyDTO.setDateOfIncorporation(dateOfIncorporation);
		companyDTO.setAddress(address);
		companyDTO.setCity(city);
		companyDTO.setState(state);
		companyDTO.setPincode(pincode);
		companyDTO.setEmail(email);
		companyDTO.setPhoneNo(phoneNo);
		companyDTO.setTelephoneNo(telephoneNo);
		companyDTO.setFaxNo(faxNo);
		companyDTO.setWebsite(website);
		companyDTO.setLogoName(logoName);
		companyDTO.setActive(active);

		Company savedCompany = companyService.save(companyDTO);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Company registered successfully");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCompany(
	        @PathVariable Long id,
	        @RequestParam(value = "companyCode", required = false) String companyCode,
	        @RequestParam(value = "companyname", required = false) String companyname,
	        @RequestParam(value = "cin", required = false) String cin,
	        @RequestParam(value = "registerNo", required = false) String registerNo,
	        @RequestParam(value = "dateOfIncorporation", required = false) String dateOfIncorporation,
	        @RequestParam(value = "address", required = false) String address,
	        @RequestParam(value = "city", required = false) String city,
	        @RequestParam(value = "state", required = false) String state,
	        @RequestParam(value = "pincode", required = false) String pincode,
	        @RequestParam(value = "email", required = false) String email,
	        @RequestParam(value = "phoneNo", required = false) String phoneNo,
	        @RequestParam(value = "telephoneNo", required = false) String telephoneNo,
	        @RequestParam(value = "faxNo", required = false) String faxNo,
	        @RequestParam(value = "website", required = false) String website,
	        @RequestParam(value = "logoName", required = false) MultipartFile logoName,
	        @RequestParam(value = "active", required = false) Boolean active) throws IOException {

	    CompanyDTO companyDTO = new CompanyDTO();
	    companyDTO.setCompanyCode(companyCode);
	    companyDTO.setCompanyname(companyname);
	    companyDTO.setCin(cin);
	    companyDTO.setRegisterNo(registerNo);
	    companyDTO.setDateOfIncorporation(dateOfIncorporation);
	    companyDTO.setAddress(address);
	    companyDTO.setCity(city);
	    companyDTO.setState(state);
	    companyDTO.setPincode(pincode);
	    companyDTO.setEmail(email);
	    companyDTO.setPhoneNo(phoneNo);
	    companyDTO.setTelephoneNo(telephoneNo);
	    companyDTO.setFaxNo(faxNo);
	    companyDTO.setWebsite(website);
	    companyDTO.setLogoName(logoName);
	    companyDTO.setActive(active);

	    Company updatedCompany = companyService.update(id, companyDTO);

	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Company updated successfully");
	    return ResponseEntity.ok(updatedCompany);
	}


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteOrganisation(@PathVariable Long id) {
		companyService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/activate/{id}")
	public void activateCompany(@PathVariable Long id) {
		companyService.activateById(id);
	}

}