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
import org.springframework.web.multipart.MultipartFile;

import com.orchasp.app.induslockbox.dto.DirectorForm;
import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.Director;
import com.orchasp.app.induslockbox.repository.CompanyRepository;
import com.orchasp.app.induslockbox.repository.DirectorRepository;
@Service
public class DirectorService {

    @Autowired
    private DirectorRepository directorRepository;
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
	
	public List<Director> findAll() {
		List<Director> directors = directorRepository.findAll();
		directors.forEach(director -> {
			String path = getProfilePicUrl(director.getImage());
			director.setImage(path);
		});
		return directors;
	}


	public Director findById(Long id) {
		Director d = directorRepository.findById(id).orElse(null);
		if (d != null) {
			String path = getProfilePicUrl(d.getImage());
			d.setImage(path);
		}
		return d;
	}
	
	public Director updateDirector(Long id, DirectorForm updatedDirector) throws IOException {
	    Director existingDirector = directorRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Director not found with id " + id));

	    if (updatedDirector.getName() != null) {
	        existingDirector.setName(updatedDirector.getName());
	    }
	    if (updatedDirector.getEmail() != null) {
	        existingDirector.setEmail(updatedDirector.getEmail());
	    }
	    if (updatedDirector.getDinNo() != null) {
	        existingDirector.setDinNo(updatedDirector.getDinNo());
	    }
	    if (updatedDirector.getAddress() != null) {
	        existingDirector.setAddress(updatedDirector.getAddress());
	    }
	    if (updatedDirector.getDateOfAppointment() != null) {
	        existingDirector.setDateOfAppointment(updatedDirector.getDateOfAppointment());
	    }
	    if (updatedDirector.getDateOfExit() != null) {
	        existingDirector.setDateOfExit(updatedDirector.getDateOfExit());
	    }
	    if (updatedDirector.getPanNo() != null) {
	        existingDirector.setPanNo(updatedDirector.getPanNo());
	    }
	    if (updatedDirector.getPassportNo() != null) {
	        existingDirector.setPassportNo(updatedDirector.getPassportNo());
	    }
	    if (updatedDirector.getAadharNo() != null) {
	        existingDirector.setAadharNo(updatedDirector.getAadharNo());
	    }
	    if (updatedDirector.getDesignation() != null) {
	        existingDirector.setDesignation(updatedDirector.getDesignation());
	    }
	    if (updatedDirector.isActive() != false) {
	        existingDirector.setActive(updatedDirector.isActive());
	    }

	    if (updatedDirector.getImage() != null && !updatedDirector.getImage().isEmpty()) {
	        String imagePath = uploadFile(updatedDirector.getImage());
	        existingDirector.setImage(imagePath);
	    }

	    if (updatedDirector.getCreatedBy() != null) {
	        existingDirector.setCreatedBy(updatedDirector.getCreatedBy());
	    }
	    if (updatedDirector.getCreatedDate() != null) {
	        existingDirector.setCreatedDate(updatedDirector.getCreatedDate());
	    }
	    if (updatedDirector.getUpdatedBy() != null) {
	        existingDirector.setUpdatedBy(updatedDirector.getUpdatedBy());
	    }
	    existingDirector.setUpdatedDate(LocalDateTime.now());

	    if (updatedDirector.getCompany() != null) {
	        Company updatedCompany = updatedDirector.getCompany();
	        Company existingCompany = existingDirector.getCompany();
	        if (existingCompany != null) {
	            if (updatedCompany.getCompanyCode() != null) {
	                existingCompany.setCompanyCode(updatedCompany.getCompanyCode());
	            }
	            if (updatedCompany.getCin() != null) {
	                existingCompany.setCin(updatedCompany.getCin());
	            }
	            if (updatedCompany.getCompanyname() != null) {
	                existingCompany.setCompanyname(updatedCompany.getCompanyname());
	            }
	            if (updatedCompany.getDateOfIncorporation() != null) {
	                existingCompany.setDateOfIncorporation(updatedCompany.getDateOfIncorporation());
	            }
	            if (updatedCompany.getRegisterNo() != null) {
	                existingCompany.setRegisterNo(updatedCompany.getRegisterNo());
	            }
	            if (updatedCompany.getPhoneNo() != null) {
	                existingCompany.setPhoneNo(updatedCompany.getPhoneNo());
	            }
	            if (updatedCompany.getEmail() != null) {
	                existingCompany.setEmail(updatedCompany.getEmail());
	            }
	            if (updatedCompany.getAddress() != null) {
	                existingCompany.setAddress(updatedCompany.getAddress());
	            }
	            if (updatedCompany.getWebsite() != null) {
	                existingCompany.setWebsite(updatedCompany.getWebsite());
	            }
	            if (updatedCompany.getTelephoneNo() != null) {
	                existingCompany.setTelephoneNo(updatedCompany.getTelephoneNo());
	            }
	            if (updatedCompany.getFaxNo() != null) {
	                existingCompany.setFaxNo(updatedCompany.getFaxNo());
	            }
	            if (updatedCompany.getCity() != null) {
	                existingCompany.setCity(updatedCompany.getCity());
	            }
	            if (updatedCompany.getState() != null) {
	                existingCompany.setState(updatedCompany.getState());
	            }
	            if (updatedCompany.getPincode() != null) {
	                existingCompany.setPincode(updatedCompany.getPincode());
	            }
	            existingCompany.setUpdatedBy(updatedCompany.getUpdatedBy());
	            existingCompany.setUpdatedDate(LocalDateTime.now());
	            existingCompany.setActive(updatedCompany.isActive());
	            companyRepository.save(existingCompany);
	        } else {
	            companyRepository.save(updatedCompany);
	            existingDirector.setCompany(updatedCompany);
	        }
	    }

	    return directorRepository.save(existingDirector);
	}



    public Director save(DirectorForm director) throws IOException {
		Long companyId = director.getCompany().getCompanyid();
		Director d = new Director();
		String imagePath = uploadFile(director.getImage());
		Optional<Company> company = companyRepository.findById(companyId);
		if (company.isPresent()) {
			Company c = company.get();
			d.setCompany(c);
			d.setActive(true);
			d.setName(director.getName());
			d.setAadharNo(director.getAadharNo());
			d.setAddress(director.getAddress());
			d.setDinNo(director.getDinNo());
			d.setDesignation(director.getDesignation());
			d.setDateOfAppointment(director.getDateOfAppointment());
			d.setDateOfExit(director.getDateOfExit());
			d.setCreatedDate(LocalDateTime.now());
			d.setPanNo(director.getPanNo());
			d.setEmail(director.getEmail());
			d.setPassportNo(director.getPassportNo());
			d.setName(director.getName());
			d.setMobileNo(director.getMobileNo());
			d.setImage(imagePath);
			if(director.getCreatedBy() != null)
				d.setCreatedBy(director.getCreatedBy());
			if(director.getUpdatedBy() != null)
				d.setUpdatedBy(director.getUpdatedBy());
			return directorRepository.save(d);
		} else {
			throw new RuntimeException("Company not found with id " + companyId);
		}
	}
  
    
    public List<Director> findByCompany_id(Long company_id) {
		return directorRepository.findByCompany_id(company_id);
	}

    public void deleteById(Long id) {
    	Optional<Director> d =directorRepository.findById(id);
    	if(d.isPresent()) {
    		Director director=d.get();
    		director.setActive(false);
    		directorRepository.save(director);
    	}else {
			throw new RuntimeException("Company not found with id " + id);
		}
    }


    public void activateById(Long id) {
		Optional<Director> DirectorOpt =directorRepository.findById(id);
		if (DirectorOpt.isPresent()) {
			Director director = DirectorOpt.get();
			director.setActive(true);
			directorRepository.save(director);
		}
	}
    public void saveImage(Long id, byte[] image) {
		Optional<Director> existingDirectorOpt = directorRepository.findById(id);
		if (existingDirectorOpt.isPresent()) {
			Director existingDirector = existingDirectorOpt.get();
//			existingDirector.setImage(image);
			directorRepository.save(existingDirector);
		} else {
			throw new RuntimeException("Director not found with id " + id);
		}
	}

	public Optional<Director> findByIdWithImage(Long id) {
		return directorRepository.findById(id);
	}


}