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

import com.orchasp.app.induslockbox.entity.MCA;
import com.orchasp.app.induslockbox.entity.TDS;
import com.orchasp.app.induslockbox.service.MCAService;

@RestController
@RequestMapping("/api/admin/mca")
public class MCAController {

    @Autowired
    private MCAService mcaService;

    @GetMapping("/fetchall")
    public List<MCA> getAllMCAs() {
        return mcaService.findAll();
    }

    @GetMapping("/fetchbyid/{id}")
    public ResponseEntity<MCA> getMCAById(@PathVariable Long id) {
        Optional<MCA> mcaOpt = mcaService.findById(id);
        if (mcaOpt.isPresent()) {
            return ResponseEntity.ok(mcaOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/company/{company_id}")
    public ResponseEntity<List<MCA>> getMCAByCompanyId(@PathVariable Long company_id) {
        List<MCA> mcaDetails = mcaService.findByCompany_id(company_id);
        return ResponseEntity.ok(mcaDetails);
    }

    @PostMapping("/save")
    public MCA createMCA(@RequestBody MCA mca) {
        return mcaService.save(mca);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MCA> updateMCA(@PathVariable Long id, @RequestBody MCA updatedMCA) {
        try {
            MCA updatedMCAEntity = mcaService.updateMCA(id, updatedMCA);
            return ResponseEntity.ok(updatedMCAEntity);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMCA(@PathVariable Long id) {
        try {
            mcaService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Void> activateMCA(@PathVariable Long id) {
        try {
            mcaService.activateById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/decrypt/V2Password/{id}")
    public String getDecryptV2Password(@PathVariable Long id) {
    	return mcaService.GetDecryptV2Password(id);
    }

    @GetMapping("/decrypt/V3Password/{id}")
    public String getDecryptV3Password(@PathVariable Long id) {
    	return mcaService.GetDecryptV3Password(id);
    }
}
