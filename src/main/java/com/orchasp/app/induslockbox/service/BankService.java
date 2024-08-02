package com.orchasp.app.induslockbox.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orchasp.app.induslockbox.entity.Bank;
import com.orchasp.app.induslockbox.entity.Company;
import com.orchasp.app.induslockbox.repository.BankRepository;
import com.orchasp.app.induslockbox.repository.CompanyRepository;

@Service
public class BankService {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private PasswordService passwordService;

	public List<Bank> findAll() {
		return bankRepository.findAll();
	}

	public List<Bank> findByCompanyId(Long companyId) {
		return bankRepository.findByCompany_id(companyId);
	}

	public Optional<Bank> findById(Long id) {
		return bankRepository.findById(id);
	}

	public Bank updateBank(Long id, Bank updatedBank) {
		Optional<Bank> existingBankOpt = bankRepository.findById(id);
		if (existingBankOpt.isPresent()) {
			Bank existingBank = existingBankOpt.get();

			// Update basic fields
			existingBank.setAccountHolderName(updatedBank.getAccountHolderName());
			existingBank.setBankAccountNumber(updatedBank.getBankAccountNumber());
			existingBank.setIfccode(updatedBank.getIfccode());
			existingBank.setBankName(updatedBank.getBankName());
			existingBank.setBranch(updatedBank.getBranch());
			existingBank.setMcircode(updatedBank.getMcircode());
			existingBank.setEmail(updatedBank.getEmail());
			if(!updatedBank.getTranspassword().equals(existingBank.getTranspassword()))
			existingBank.setTranspassword(passwordService.encrypt(updatedBank.getTranspassword()));
			existingBank.setAccountType(updatedBank.getAccountType());
			if(!updatedBank.getLoginpassword().equals(existingBank.getLoginpassword()))
			existingBank.setLoginpassword(passwordService.encrypt(updatedBank.getLoginpassword()));
			existingBank.setPrimarysignatory(updatedBank.getPrimarysignatory());
			existingBank.setSecondarysignatory(updatedBank.getSecondarysignatory());
			existingBank.setLoginid(updatedBank.getLoginid());
			existingBank.setMobileNo(updatedBank.getMobileNo());

			existingBank.setCreatedBy(updatedBank.getCreatedBy());
			existingBank.setCreatedDate(updatedBank.getCreatedDate());
			existingBank.setUpdatedBy(updatedBank.getUpdatedBy());
			existingBank.setUpdatedDate(LocalDateTime.now());
			existingBank.setActive(updatedBank.isActive());

			// Update or save company
			Company updatedCompany = updatedBank.getCompany();
			if (updatedCompany != null) {
				Company existingCompany = existingBank.getCompany();
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
				} else {
					companyRepository.save(updatedCompany);
					existingBank.setCompany(updatedCompany);
				}
			}

			return bankRepository.save(existingBank);
		} else {
			throw new RuntimeException("Bank not found with id " + id);
		}
	}

	public Bank save(Bank bank) {
		Long companyId = bank.getCompany().getCompanyid();
		Optional<Company> company = companyRepository.findById(companyId);
		if (company.isPresent()) {
			Company c = company.get();
			bank.setCompany(c);
			bank.setActive(true);
			bank.setCreatedDate(LocalDateTime.now());
			bank.setLoginpassword(passwordService.encrypt(bank.getLoginpassword()));
			bank.setTranspassword(passwordService.encrypt(bank.getTranspassword()));
			return bankRepository.save(bank);
		} else {
			throw new RuntimeException("Company not found with id " + companyId);
		}
	}

	public void deleteById(Long id) {
		Optional<Bank> bankOpt = bankRepository.findById(id);
		if (bankOpt.isPresent()) {
			Bank bank = bankOpt.get();
			bank.setActive(false);
			bankRepository.save(bank);
		} else {
			throw new RuntimeException("Bank not found with id " + id);
		}
	}

	public void activateById(Long id) {
		Optional<Bank> bankOpt = bankRepository.findById(id);
		if (bankOpt.isPresent()) {
			Bank bank = bankOpt.get();
			bank.setActive(true);
			bankRepository.save(bank);
		} else {
			throw new RuntimeException("Bank not found with id " + id);
		}
	}

	public String GetDecryptLoginPassword(Long id) {
		Optional<Bank> bankOpt = bankRepository.findById(id);
		if (bankOpt.isPresent()) {
			Bank bank = bankOpt.get();
			return passwordService.decrypt(bank.getLoginpassword());
		} else {
			throw new RuntimeException("Bank not found with id " + id);
		}

	}

	public String GetDecryptTransactionPassword(Long id) {
		Optional<Bank> bankOpt = bankRepository.findById(id);
		if (bankOpt.isPresent()) {
			Bank bank = bankOpt.get();
			return passwordService.decrypt(bank.getTranspassword());
		} else {
			throw new RuntimeException("Bank not found with id " + id);
		}

	}
}
