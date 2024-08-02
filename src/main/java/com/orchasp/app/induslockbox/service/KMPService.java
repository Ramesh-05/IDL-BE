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

import com.orchasp.app.induslockbox.dto.KMPDTO;
import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.KMP;
import com.orchasp.app.induslockbox.repository.CompanyRepository;
import com.orchasp.app.induslockbox.repository.KMPRepository;

@Service
public class KMPService {

	@Autowired
	private KMPRepository KMPRepository;

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



	public List<KMP> findAll() {
		List<KMP> kmp = KMPRepository.findAll();
		kmp.forEach(kmps -> {
			String path = getProfilePicUrl(kmps.getImage());
			kmps.setImage(path);
			String path1 = getProfilePicUrl(kmps.getResume());
			kmps.setResume(path1);
		});
		return kmp;
	}

	public List<KMP> findByCompany_id(Long company_id) {
		return KMPRepository.findByCompany_id(company_id);
	}

	public KMP findById(Long id) {
		KMP k = KMPRepository.findById(id).orElse(null);
		if (k != null) {
			String path = getProfilePicUrl(k.getImage());
			k.setImage(path);
			String path1 = getProfilePicUrl(k.getResume());
			k.setResume(path1);
		}
		return k;
	}

	public KMP updateKMP(Long id, KMPDTO updatedKMP) throws IOException {
	    Optional<KMP> existingKMPOpt = KMPRepository.findById(id);
	    if (existingKMPOpt.isPresent()) {
	        KMP existingKMP = existingKMPOpt.get();

	        if (updatedKMP.getEmail() != null) {
	            existingKMP.setEmail(updatedKMP.getEmail());
	        }
	        if (updatedKMP.getName() != null) {
	            existingKMP.setName(updatedKMP.getName());
	        }
	        if (updatedKMP.getDesignation() != null) {
	            existingKMP.setDesignation(updatedKMP.getDesignation());
	        }
	        if (updatedKMP.getMobileNo() != null) {
	            existingKMP.setMobileNo(updatedKMP.getMobileNo());
	        }
	        if (updatedKMP.getAddress() != null) {
	            existingKMP.setAddress(updatedKMP.getAddress());
	        }
	        if (updatedKMP.getState() != null) {
	            existingKMP.setState(updatedKMP.getState());
	        }
	        if (updatedKMP.getAadharNo() != null) {
	            existingKMP.setAadharNo(updatedKMP.getAadharNo());
	        }
	        if (updatedKMP.getPassportNo() != null) {
	            existingKMP.setPassportNo(updatedKMP.getPassportNo());
	        }
	        if (updatedKMP.getPanNo() != null) {
	            existingKMP.setPanNo(updatedKMP.getPanNo());
	        }
	        if (updatedKMP.getImage() != null && !updatedKMP.getImage().isEmpty()) {
		        String imagePath = uploadFile(updatedKMP.getImage());
		        existingKMP.setImage(imagePath);
		    }
	        if (updatedKMP.getResume() != null && !updatedKMP.getResume().isEmpty()) {
	        	String resumePath = uploadFile(updatedKMP.getResume());
	            existingKMP.setResume(resumePath);
	        }
	        if (updatedKMP.getCreatedBy() != null) {
	            existingKMP.setCreatedBy(updatedKMP.getCreatedBy());
	        }
	        if (updatedKMP.getCreatedDate() != null) {
	            existingKMP.setCreatedDate(updatedKMP.getCreatedDate());
	        }
	        if (updatedKMP.getUpdatedBy() != null) {
	            existingKMP.setUpdatedBy(updatedKMP.getUpdatedBy());
	        }
	        existingKMP.setUpdatedDate(LocalDateTime.now());
	        existingKMP.setActive(updatedKMP.isActive());

	        if (updatedKMP.getCompany() != null) {
	            Company updatedCompany = updatedKMP.getCompany();
	            Company existingCompany = existingKMP.getCompany();
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
	                existingKMP.setCompany(updatedCompany);
	            }
	        }

	        return KMPRepository.save(existingKMP);
	    } else {
	        throw new RuntimeException("KMP not found with id " + id);
	    }
	}


	public KMP save(KMPDTO kmpDto) throws IOException {
	    Long companyId = kmpDto.getCompanyid();
	    Optional<Company> companyOpt = companyRepository.findById(companyId);
	    String imagePath = uploadFile(kmpDto.getImage());
	    String resumePath = uploadFile(kmpDto.getResume());

	    if (companyOpt.isPresent()) {
	        Company company = companyOpt.get();
	        KMP kmp = new KMP();

	        kmp.setCompany(company);
	        kmp.setName(kmpDto.getName());
	        kmp.setEmail(kmpDto.getEmail());
	        kmp.setAadharNo(kmpDto.getAadharNo());
	        kmp.setPassportNo(kmpDto.getPassportNo());
	        kmp.setPanNo(kmpDto.getPanNo());
	        kmp.setDesignation(kmpDto.getDesignation());
	        kmp.setAddress(kmpDto.getAddress());
	        kmp.setState(kmpDto.getState());
	        kmp.setMobileNo(kmpDto.getMobileNo());
	        kmp.setImage(imagePath);
	        kmp.setResume(resumePath);

	        kmp.setActive(true);
	        kmp.setCreatedDate(LocalDateTime.now());
	        if (kmpDto.getCreatedBy() != null) {
	            kmp.setCreatedBy(kmpDto.getCreatedBy());
	        }
	        if (kmpDto.getUpdatedBy() != null) {
	            kmp.setUpdatedBy(kmpDto.getUpdatedBy());
	        }
	        kmp.setUpdatedDate(LocalDateTime.now());

	        return KMPRepository.save(kmp);
	    } else {
	        throw new RuntimeException("Company not found with id " + companyId);
	    }
	}


	public void deleteById(Long id) {
		Optional<KMP> KMPOpt = KMPRepository.findById(id);
		if (KMPOpt.isPresent()) {
			KMP b = KMPOpt.get();
			b.setActive(false);
			KMPRepository.save(b);
		}

	}

	public void activateById(Long id) {
		Optional<KMP> KMPOpt = KMPRepository.findById(id);
		if (KMPOpt.isPresent()) {
			KMP KMP = KMPOpt.get();
			KMP.setActive(true);
			KMPRepository.save(KMP);
		}
	}
}