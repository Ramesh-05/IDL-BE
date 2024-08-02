package com.orchasp.app.induslockbox.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class StateController {
	
	@GetMapping("/states")
    public List<State> getStates() {
        return Arrays.asList(
                new State("Jammu & Kashmir", "01"),
                new State("Himachal Pradesh", "02"),
                new State("Punjab", "03"),
                new State("Chandigarh", "04"),
                new State("Uttarakhand", "05"),
                new State("Haryana", "06"),
                new State("Delhi", "07"),
                new State("Rajasthan", "08"),
                new State("Uttar Pradesh", "09"),
                new State("Bihar", "10"),
                new State("Sikkim", "11"),
                new State("Arunachal Pradesh", "12"),
                new State("Nagaland", "13"),
                new State("Manipur", "14"),
                new State("Mizoram", "15"),
                new State("Tripura", "16"),
                new State("Meghalaya", "17"),
                new State("Assam", "18"),
                new State("West Bengal", "19"),
                new State("Jharkhand", "20"),
                new State("Odisha", "21"),
                new State("Chhattisgarh", "22"),
                new State("Madhya Pradesh", "23"),
                new State("Gujarat", "24"),
                new State("Daman & Diu", "25"),
                new State("Dadra and Nagar Haveli", "26"),
                new State("Maharashtra", "27"),
                new State("Maharashtra(Old)", "28"),
                new State("Karnataka", "29"),
                new State("Goa", "30"),
                new State("Lakshadweep", "31"),
                new State("Kerala", "32"),
                new State("Tamil Nadu", "33"),
                new State("Puducherry", "34"),
                new State("Andaman and Nicobar Islands", "35"),
                new State("Telangana", "36"),
                new State("Andhra Pradesh", "37"),
                new State("Ladakh", "38")
        );
    }

    static class State {
        private String name;
        private String gstCode;

        public State(String name, String gstCode) {
            this.name = name;
            this.gstCode = gstCode;
        }

        public String getName() {
            return name;
        }

        public String getGstCode() {
            return gstCode;
        }
    }

}