
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.orchasp.app.induslockbox.entity.GST;
import com.orchasp.app.induslockbox.service.GSTService;

@RestController
@RequestMapping("/api/admin/gst")
public class GSTController {

    @Autowired
    private GSTService GSTService;

    @GetMapping("/fetchall")
    public List<GST> getAllGSTs() {
        return GSTService.findAll();
    }

    @GetMapping("/fetchbyid/{id}")
    public ResponseEntity<GST> getGSTById(@PathVariable Long id) {
        Optional<GST> GST = GSTService.findById(id);
        return GST.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public GST createGST(@RequestBody GST GST,@RequestParam String createdBy,@RequestParam Long companyid) {
        return GSTService.save(GST,createdBy,companyid);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GST> updateGST(@PathVariable Long id, @RequestBody GST gstDetails) {
        try {
            GST updatedGST = GSTService.updateGST(id, gstDetails);
            return ResponseEntity.ok(updatedGST);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGST(@PathVariable Long id) {
    	GSTService.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/activate/{id}")
    public void activateGST(@PathVariable Long id) {
        GSTService.activateById(id);
    }
    
    @GetMapping("/company/{company_id}")
    public ResponseEntity<List<GST>> getGSTByCompanyId(@PathVariable Long company_id) {
        List<GST> GSTDetails = GSTService.findByCompany_id(company_id);
        return ResponseEntity.ok(GSTDetails);
    }
    
    @GetMapping("/decrypt/{id}")
    public String getDecryptPassword(@PathVariable Long id) {
    	return GSTService.GetDecryptPassword(id);
    }

}