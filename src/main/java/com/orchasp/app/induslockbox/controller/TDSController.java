package com.orchasp.app.induslockbox.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orchasp.app.induslockbox.entity.TDS;
import com.orchasp.app.induslockbox.service.TDSService;


@RestController
@RequestMapping("/api/admin/tds")
public class TDSController {

	    @Autowired
	    private TDSService TDSService;

	    @GetMapping("/fetchall")
	    public List<TDS> getAllTDS() {
	        return TDSService.findAll();
	    }

	    @GetMapping("/fetchbyid/{id}")
	    public ResponseEntity<TDS> getDirectorById(@PathVariable Long id) {
	        Optional<TDS> tds = TDSService.findById(id);
	        return tds.map(ResponseEntity::ok)
	                   .orElseGet(() -> ResponseEntity.notFound().build());
	    }
	    
	    @GetMapping("/company/{company_id}")
	    public ResponseEntity<List<TDS>> getDirectorByCompanyId(@PathVariable Long company_id) {
	        List<TDS> TDSDetails = TDSService.findByCompany_id(company_id);
	        return ResponseEntity.ok(TDSDetails);
	    }

	    @PostMapping("/save")
	    public TDS createBank(@RequestBody TDS tds) {
	        return TDSService.save(tds);
	    }

	    @PutMapping("/update/{id}")
	    public TDS updateDirector(@PathVariable Long id, @RequestBody TDS tdsDetails) {
	    	return TDSService.updateTDS(id, tdsDetails);
	    }

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
	    	TDSService.deleteById(id);
	        return ResponseEntity.ok().build();
	    }
	    @PatchMapping("/activate/{id}")
	    public void activateDirector(@PathVariable Long id) {
	    	TDSService.activateById(id);
	    }
	    @GetMapping("/decrypt/{id}")
	    public String getDecryptPassword(@PathVariable Long id) {
	        return TDSService.getDecryptPassword(id);
	    }
	}
