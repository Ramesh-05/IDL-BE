package com.orchasp.app.induslockbox.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.orchasp.app.induslockbox.dto.DirectorForm;
import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.Director;
import com.orchasp.app.induslockbox.service.CompanyService;
import com.orchasp.app.induslockbox.service.DirectorService;

@RestController
@RequestMapping("/api/admin/directors")
public class DirectorController {

	@Autowired
	private DirectorService directorService;
	@Autowired
	private CompanyService companyService;

	@GetMapping("/fetchall")
	public List<Director> getAllBanks() {
		return directorService.findAll();
	}

	@GetMapping("/fetchbyid/{id}")
	public ResponseEntity<Director> getDirectorById(@PathVariable Long id) {
		Director director = directorService.findById(id);
		return ResponseEntity.ok(director);
	}

	@GetMapping("/company/{company_id}")
	public ResponseEntity<List<Director>> getDirectorByCompanyId(@PathVariable Long company_id) {
		List<Director> DirectorDetails = directorService.findByCompany_id(company_id);
		return ResponseEntity.ok(DirectorDetails);
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveDirector(@RequestParam("name") String name, @RequestParam("email") String email,
	                                      @RequestParam("dinNo") Long dinNo, @RequestParam("aadharNo") Long aadharNo,
	                                      @RequestParam("panNo") String panNo, @RequestParam("passportNo") String passportNo,
	                                      @RequestParam("designation") String designation,
	                                      @RequestParam("dateOfAppointment") String dateOfAppointment,
	                                      @RequestParam("mobileNo") Long mobileNo, @RequestParam("address") String address,
	                                      @RequestParam("active") Boolean active, @RequestParam("image") MultipartFile image,
	                                      @RequestParam("companyid") Long companyid) throws IOException {

	   
	    Company company = companyService.findById(companyid);
	    if (company == null) {
	        return ResponseEntity.badRequest().body("Invalid company ID");
	    }

	    DirectorForm directorForm = new DirectorForm();
	    directorForm.setName(name);
	    directorForm.setEmail(email);
	    directorForm.setDinNo(dinNo);
	    directorForm.setAadharNo(aadharNo);
	    directorForm.setPanNo(panNo);
	    directorForm.setPassportNo(passportNo);
	    directorForm.setDesignation(designation);
	    directorForm.setDateOfAppointment(dateOfAppointment);
	    directorForm.setMobileNo(mobileNo);
	    directorForm.setAddress(address);
	    directorForm.setActive(active);
	    directorForm.setImage(image);
	    directorForm.setCompany(company); 

	    Director savedDirector = directorService.save(directorForm);

	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Director registered successfully");
	    return ResponseEntity.ok(savedDirector);
	}


	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateDirector(@PathVariable Long id,
	                                         @RequestParam(value = "name", required = false) String name,
	                                         @RequestParam(value = "email", required = false) String email,
	                                         @RequestParam(value = "dinNo", required = false) Long dinNo,
	                                         @RequestParam(value = "aadharNo", required = false) Long aadharNo,
	                                         @RequestParam(value = "panNo", required = false) String panNo,
	                                         @RequestParam(value = "passportNo", required = false) String passportNo,
	                                         @RequestParam(value = "designation", required = false) String designation,
	                                         @RequestParam(value = "dateOfAppointment", required = false) String dateOfAppointment,
	                                         @RequestParam(value = "dateOfExit", required = false) String dateOfExit,
	                                         @RequestParam(value = "mobileNo", required = false) Long mobileNo,
	                                         @RequestParam(value = "address", required = false) String address,
	                                         @RequestParam(value = "active", required = false) Boolean active,
	                                         @RequestParam(value = "image", required = false) MultipartFile image,
	                                         @RequestParam(value = "companyid", required = false) Long companyid) throws IOException {

	    DirectorForm directorForm = new DirectorForm();
	    directorForm.setName(name);
	    directorForm.setEmail(email);
	    directorForm.setDinNo(dinNo);
	    directorForm.setAadharNo(aadharNo);
	    directorForm.setPanNo(panNo);
	    directorForm.setPassportNo(passportNo);
	    directorForm.setDesignation(designation);
	    directorForm.setDateOfAppointment(dateOfAppointment);
	    directorForm.setDateOfExit(dateOfExit);
	    directorForm.setMobileNo(mobileNo);
	    directorForm.setAddress(address);
	    directorForm.setActive(active);
	    directorForm.setImage(image);
	    directorForm.setCompanyid(companyid);

	    Director updatedDirector = directorService.updateDirector(id, directorForm);

	    Map<String, String> response = new HashMap<>();
	    response.put("message", "Director updated successfully");
	    return ResponseEntity.ok(updatedDirector);
	}


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
		directorService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/uploadImage/{id}")
	public ResponseEntity<Void> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
		try {
			byte[] imageBytes = image.getBytes();
			directorService.saveImage(id, imageBytes);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PatchMapping("/activate/{id}")
	public void activateDirector(@PathVariable Long id) {
		directorService.activateById(id);
	}

}