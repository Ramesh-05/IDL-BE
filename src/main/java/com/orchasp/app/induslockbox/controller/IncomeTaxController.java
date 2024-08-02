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

import com.orchasp.app.induslockbox.entity.IncomeTax;
import com.orchasp.app.induslockbox.service.IncomeTaxService;

@RestController
@RequestMapping("/api/admin/incometax")
public class IncomeTaxController {

    @Autowired
    private IncomeTaxService incomeTaxService;

    @GetMapping("/fetchall")
    public List<IncomeTax> getAllIncomeTaxes() {
        return incomeTaxService.findAll();
    }

    @GetMapping("/fetchbyid/{id}")
    public ResponseEntity<IncomeTax> getIncomeTaxById(@PathVariable Long id) {
        Optional<IncomeTax> incomeTax = incomeTaxService.findById(id);
        return incomeTax.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public IncomeTax createIncomeTax(@RequestBody IncomeTax incomeTax) {
        return incomeTaxService.save(incomeTax);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<IncomeTax> updateIncomeTax(@PathVariable Long id, @RequestBody IncomeTax incomeTaxDetails) {
        try {
            IncomeTax updatedIncomeTax = incomeTaxService.updateIncomeTax(id, incomeTaxDetails);
            return ResponseEntity.ok(updatedIncomeTax);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIncomeTax(@PathVariable Long id) {
        incomeTaxService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Void> activateIncomeTax(@PathVariable Long id) {
        try {
            incomeTaxService.activateById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<IncomeTax>> getIncomeTaxByCompanyId(@PathVariable Long companyId) {
        List<IncomeTax> incomeTaxes = incomeTaxService.findByCompany_id(companyId);
        return ResponseEntity.ok(incomeTaxes);
    }
    @GetMapping("/decrypt/{id}")
    public String getDecryptPassword(@PathVariable Long id) {
        return incomeTaxService.GetDecryptPassword(id);
    }
}
