package com.ing.mortgage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ing.mortgage.service.MortgageService;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = {"*","/"},origins = {"*","/"})
public class MortgageController {
@Autowired
MortgageService mortgageService;



}
