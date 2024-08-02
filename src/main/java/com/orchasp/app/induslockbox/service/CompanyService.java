package com.orchasp.app.induslockbox.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import com.orchasp.app.induslockbox.dto.CompanyDTO;
import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Value("${file.upload-dir}")
	private String folderPath;

	public String uploadFile(MultipartFile file) {
		if (file.isEmpty()) {
			return "Please select a file to upload.";
		}
		try {
			String originalFileName = file.getOriginalFilename();
			String uniqueFileName = UUID.randomUUID().toString().substring(0, 5) + System.currentTimeMillis() + "_"
					+ originalFileName;
			byte[] bytes = file.getBytes();
			Path path = Paths.get(folderPath + File.separator + uniqueFileName);
			Files.write(path, bytes);
			return uniqueFileName;
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload '" + file.getOriginalFilename() + "'";
		}
	}

	public String getProfilePicUrl(String profilePicPath) {
		if (profilePicPath == null || profilePicPath.isEmpty()) {
			return null;
		}
		return "/files/get/" + profilePicPath;
	}

	public void deleteFile(String profilePic) {
		Path path = Paths.get(folderPath + File.separator + profilePic);
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Company> findAll() {
		List<Company> companies = companyRepository.findAll();
		companies.forEach(company -> {
			String path = getProfilePicUrl(company.getImage());
			company.setImage(path);
		});
		return companies;
	}

	public Company findById(Long id) {
		Company c = companyRepository.findById(id).orElse(null);
		if (c != null) {
			String path = getProfilePicUrl(c.getImage());
			c.setImage(path);
		}
		return c;
	}

	public Company save(CompanyDTO company) throws IOException {
		Company c = new Company();
		String imagePath = uploadFile(company.getLogoName());
		c.setImage(imagePath);
		c.setActive(true);
		c.setAddress(company.getAddress());
		c.setCin(company.getCin());
		c.setCity(company.getCity());
		c.setCompanyCode(company.getCompanyCode());
		c.setCompanyname(company.getCompanyname());
		c.setPhoneNo(company.getPhoneNo());
		c.setCreatedBy(company.getCreatedBy());
		c.setCreatedDate(LocalDateTime.now());
		c.setDateOfIncorporation(company.getDateOfIncorporation());
		c.setEmail(company.getEmail());
		c.setFaxNo(company.getFaxNo());
		c.setPincode(company.getPincode());
		c.setRegisterNo(company.getRegisterNo());
		c.setState(company.getState());
		c.setTelephoneNo(company.getTelephoneNo());
		c.setUpdatedBy(company.getUpdatedBy());
		c.setUpdatedDate(company.getUpdatedDate());
		c.setWebsite(company.getWebsite());
		return companyRepository.save(c);
	}

	public Company update(Long id, CompanyDTO companyDTO) throws IOException {
	    Optional<Company> existingCompanyOpt = companyRepository.findById(id);
	    if (!existingCompanyOpt.isPresent()) {
	        throw new RuntimeException("Company not found");
	    }

	    Company existingCompany = existingCompanyOpt.get();

	    if (companyDTO.getLogoName() != null && !companyDTO.getLogoName().isEmpty()) {
	        String imagePath = uploadFile(companyDTO.getLogoName());
	        existingCompany.setImage(imagePath);
	    }
	    if (companyDTO.isActive() != false) {
	        existingCompany.setActive(companyDTO.isActive());
	    }
	    if (companyDTO.getAddress() != null) {
	        existingCompany.setAddress(companyDTO.getAddress());
	    }
	    if (companyDTO.getCin() != null) {
	        existingCompany.setCin(companyDTO.getCin());
	    }
	    if (companyDTO.getCity() != null) {
	        existingCompany.setCity(companyDTO.getCity());
	    }
	    if (companyDTO.getCompanyCode() != null) {
	        existingCompany.setCompanyCode(companyDTO.getCompanyCode());
	    }
	    if (companyDTO.getCompanyname() != null) {
	        existingCompany.setCompanyname(companyDTO.getCompanyname());
	    }
	    if (companyDTO.getPhoneNo() != null) {
	        existingCompany.setPhoneNo(companyDTO.getPhoneNo());
	    }
	    if (companyDTO.getDateOfIncorporation() != null) {
	        existingCompany.setDateOfIncorporation(companyDTO.getDateOfIncorporation());
	    }
	    if (companyDTO.getEmail() != null) {
	        existingCompany.setEmail(companyDTO.getEmail());
	    }
	    if (companyDTO.getFaxNo() != null) {
	        existingCompany.setFaxNo(companyDTO.getFaxNo());
	    }
	    if (companyDTO.getPincode() != null) {
	        existingCompany.setPincode(companyDTO.getPincode());
	    }
	    if (companyDTO.getRegisterNo() != null) {
	        existingCompany.setRegisterNo(companyDTO.getRegisterNo());
	    }
	    if (companyDTO.getState() != null) {
	        existingCompany.setState(companyDTO.getState());
	    }
	    if (companyDTO.getTelephoneNo() != null) {
	        existingCompany.setTelephoneNo(companyDTO.getTelephoneNo());
	    }
	    if (companyDTO.getWebsite() != null) {
	        existingCompany.setWebsite(companyDTO.getWebsite());
	    }

	    existingCompany.setUpdatedDate(LocalDateTime.now());

	    return companyRepository.save(existingCompany);
	}



	public void deleteById(Long id) {
		Optional<Company> companyOpt = companyRepository.findById(id);
		if (companyOpt.isPresent()) {
			Company company = companyOpt.get();
			company.setActive(false);
			companyRepository.save(company);
		} else {
			throw new RuntimeException("Company not found with id " + id);
		}
	}

	public void activateById(Long id) {
		Optional<Company> companyOpt = companyRepository.findById(id);
		if (companyOpt.isPresent()) {
			Company company = companyOpt.get();
			company.setActive(true);
			companyRepository.save(company);
		} else {
			throw new RuntimeException("Company not found with id " + id);
		}
	}


}
