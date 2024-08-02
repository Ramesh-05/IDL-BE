package com.orchasp.app.induslockbox.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Bank;
import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.entity.EPF;
import com.orchasp.app.induslockbox.entity.MCA;
import com.orchasp.app.induslockbox.repository.CompanyRepository;
import com.orchasp.app.induslockbox.repository.MCARepository;

@Service
public class MCAService {

    @Autowired
    private MCARepository mcaRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordService passwordService;

    public List<MCA> findAll() {
        return mcaRepository.findAll();
    }

    public Optional<MCA> findById(Long id) {
        return mcaRepository.findById(id);
    }

    public MCA save(MCA mca) {
        Long companyId = mca.getCompany().getCompanyid();
        Optional<Company> company = companyRepository.findById(companyId);
        if (company.isPresent()) {
            mca.setCompany(company.get());
            mca.setV2password(passwordService.encrypt(mca.getV2password()));
            mca.setV3password(passwordService.encrypt(mca.getV3password()));
        } else {
            throw new RuntimeException("Company not found with id " + companyId);
        }
        mca.setCreatedDate(LocalDateTime.now());
        mca.setActive(true);
        return mcaRepository.save(mca);
    }

    public MCA updateMCA(Long id, MCA updatedMCA) {
        Optional<MCA> existingMCAOpt = mcaRepository.findById(id);
        if (existingMCAOpt.isPresent()) {
            MCA existingMCA = existingMCAOpt.get();

            existingMCA.setCin(updatedMCA.getCin());
            existingMCA.setRocName(updatedMCA.getRocName());
            existingMCA.setRegistrationNo(updatedMCA.getRegistrationNo());
            existingMCA.setDateOfIncorporation(updatedMCA.getDateOfIncorporation());
            existingMCA.setEmail(updatedMCA.getEmail());
            existingMCA.setRegisteredAddress(updatedMCA.getRegisteredAddress());
            existingMCA.setStockExchanges(updatedMCA.getStockExchanges());
            existingMCA.setClassOfCompany(updatedMCA.getClassOfCompany());
            existingMCA.setAuthorisedCapital(updatedMCA.getAuthorisedCapital());
            existingMCA.setPaidUpCapital(updatedMCA.getPaidUpCapital());
            existingMCA.setTelephone(updatedMCA.getTelephone());
            existingMCA.setMobileNo(updatedMCA.getMobileNo());
            existingMCA.setV2loginid(updatedMCA.getV2loginid());
            existingMCA.setV3loginid(updatedMCA.getV3loginid());
            existingMCA.setV2emailId(updatedMCA.getV2emailId());
            existingMCA.setV3emailId(updatedMCA.getV3emailId());
            if(!updatedMCA.getV2password().equals(existingMCA.getV2password()))
            existingMCA.setV2password(passwordService.encrypt(updatedMCA.getV2password()));
            if(!updatedMCA.getV3password().equals(existingMCA.getV3password()))
            existingMCA.setV3password(passwordService.encrypt(updatedMCA.getV3password()));
            existingMCA.setCreatedBy(updatedMCA.getCreatedBy());
            existingMCA.setUpdatedBy(updatedMCA.getUpdatedBy());
            existingMCA.setUpdatedDate(LocalDateTime.now());
            existingMCA.setActive(updatedMCA.isActive());

            Company updatedCompany = updatedMCA.getCompany();
            if (updatedCompany != null) {
                Company existingCompany = existingMCA.getCompany();
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
                    companyRepository.save(existingCompany);
                } else {
                    companyRepository.save(updatedCompany);
                    existingMCA.setCompany(updatedCompany);
                }
            }
            return mcaRepository.save(existingMCA);
        } else {
            throw new RuntimeException("MCA not found with id " + id);
        }
    }

    public void deleteById(Long id) {
        Optional<MCA> mcaOpt = mcaRepository.findById(id);
        if (mcaOpt.isPresent()) {
            MCA mca = mcaOpt.get();
            mca.setActive(false);
            mcaRepository.save(mca);
        }
    }
    public List<MCA> findByCompany_id(Long company_id) {
        return mcaRepository.findByCompany_id(company_id);
    }


    public void activateById(Long id) {
        Optional<MCA> mcaOpt = mcaRepository.findById(id);
        if (mcaOpt.isPresent()) {
            MCA mca = mcaOpt.get();
            mca.setActive(true);
            mcaRepository.save(mca);
        }
    }

    public String GetDecryptV2Password(Long id) {
        Optional<MCA> mcaOpt =mcaRepository.findById(id);
        if (mcaOpt.isPresent()) {
          MCA mca = mcaOpt.get();
            return passwordService.decrypt(mca.getV2password());
        } else {
            throw new RuntimeException("MCA not found with id " + id);
        }
    
   }
    public String GetDecryptV3Password(Long id) {
        Optional<MCA> mcaOpt =mcaRepository.findById(id);
        if (mcaOpt.isPresent()) {
          MCA mca = mcaOpt.get();
            return passwordService.decrypt(mca.getV3password());
        } else {
            throw new RuntimeException("MCA not found with id " + id);
        }
    
   }
}
