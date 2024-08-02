package com.orchasp.app.induslockbox.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.orchasp.app.induslockbox.dto.KMPDTO;
import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.KMP;
import com.orchasp.app.induslockbox.service.CompanyService;
import com.orchasp.app.induslockbox.service.KMPService;

@RestController
@RequestMapping("/api/admin/kmp")
public class KMPController {

	@Autowired
	private KMPService KMPService;
	@Autowired
	private CompanyService companyService;

	@GetMapping("/fetchall")
	public List<KMP> getAllKMPs() {
		return KMPService.findAll();
	}

	@GetMapping("/fetchbyid/{id}")
	public ResponseEntity<KMP> getKMPById(@PathVariable Long id) {
		KMP kmp = KMPService.findById(id);
		return ResponseEntity.ok(kmp);
	}

	@GetMapping("/company/{company_id}")
	public ResponseEntity<List<KMP>> getKMPByCompanyId(@PathVariable Long company_id) {
		List<KMP> KMPDetails = KMPService.findByCompany_id(company_id);
		return ResponseEntity.ok(KMPDetails);
	}

	@PostMapping("/save")
	public ResponseEntity<?> createKMP(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("aadharNo") Long aadharNo, @RequestParam("passportNo") String passportNo,
			@RequestParam("panNo") String panNo, @RequestParam("designation") String designation,
			@RequestParam("address") String address, @RequestParam("state") String state,
			@RequestParam("mobileNo") String mobileNo, @RequestParam("companyid") Long companyid,
			@RequestParam("image") MultipartFile image, @RequestParam("resume") MultipartFile resume,
			@RequestParam(value="createdBy",required = false) String createdBy, @RequestParam(value="updatedBy", required = false) String updatedBy,
			@RequestParam("active") Boolean active) throws IOException {

		Company company = companyService.findById(companyid);
		if (company == null) {
			return ResponseEntity.badRequest().body("Invalid company ID");
		}

		KMPDTO kmpDto = new KMPDTO();
		kmpDto.setName(name);
		kmpDto.setEmail(email);
		kmpDto.setAadharNo(aadharNo);
		kmpDto.setPassportNo(passportNo);
		kmpDto.setPanNo(panNo);
		kmpDto.setDesignation(designation);
		kmpDto.setAddress(address);
		kmpDto.setState(state);
		kmpDto.setMobileNo(mobileNo);
		kmpDto.setCompanyid(companyid);
		kmpDto.setCompany(company);
		kmpDto.setActive(active);
		kmpDto.setImage(image);
		kmpDto.setResume(resume);
		kmpDto.setCreatedBy(createdBy);
		kmpDto.setUpdatedBy(updatedBy);
		try {
			KMP savedKMP = KMPService.save(kmpDto);
			return new ResponseEntity<>(savedKMP, HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>("Error saving KMP: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateKMP(@PathVariable Long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "aadharNo", required = false) Long aadharNo,
			@RequestParam(value = "passportNo", required = false) String passportNo,
			@RequestParam(value = "panNo", required = false) String panNo,
			@RequestParam(value = "designation", required = false) String designation,
			@RequestParam(value = "address", required = false) String address,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "mobileNo", required = false) String mobileNo,
			@RequestParam(value = "active", required = false) Boolean active,
			@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam(value = "resume", required = false) MultipartFile resume,
			@RequestParam(value = "companyid", required = false) Long companyid,
			@RequestParam(value = "createdBy", required = false) String createdBy,
			@RequestParam(value = "updatedBy", required = false) String updatedBy) throws IOException {

		KMPDTO kmpDto = new KMPDTO();
		kmpDto.setName(name);
		kmpDto.setEmail(email);
		kmpDto.setAadharNo(aadharNo);
		kmpDto.setPassportNo(passportNo);
		kmpDto.setPanNo(panNo);
		kmpDto.setDesignation(designation);
		kmpDto.setAddress(address);
		kmpDto.setState(state);
		kmpDto.setMobileNo(mobileNo);
		kmpDto.setActive(active);
		kmpDto.setImage(image);
		kmpDto.setResume(resume);
		kmpDto.setCompanyid(companyid);
		kmpDto.setCreatedBy(createdBy);
		kmpDto.setUpdatedBy(updatedBy);

		KMP updatedKMP = KMPService.updateKMP(id, kmpDto);

		Map<String, String> response = new HashMap<>();
		response.put("message", "KMP updated successfully");
		return ResponseEntity.ok(updatedKMP);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteKMP(@PathVariable Long id) {
		KMPService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/activate/{id}")
	public void activateKMP(@PathVariable Long id) {
		KMPService.activateById(id);
	}

}