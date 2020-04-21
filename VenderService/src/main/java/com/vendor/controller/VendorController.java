package com.vendor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendor.model.VendorDetails;
import com.vendor.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {

	
	@Autowired
	Environment env;
	
	@Autowired
	VendorService vservice;
	
	@GetMapping("/sbinfo")
	public String batchinfo() {
		String port=env.getProperty("local.server.port");
		return "Spring Batch vendor Service Running port on "+port;
	}
	
	//@GetMapping("/{vname}/{itemname}/{itype}")
	//public ResponseEntity<List<VendorDetails>> searchfood(@PathVariable("vname") String vname,@PathVariable("itemname") String itemname,@PathVariable("itype") String itype)
	
	@GetMapping("/{keyword}")
	public ResponseEntity<List<VendorDetails>> searchfood(@PathVariable("keyword") String keyword)
	
	{
		//System.out.println("vendorName: "+vname+" \t itemName: "+itemname+"\t itype: "+itype);
		System.out.println("keyword: "+keyword);
		List<VendorDetails> foodsearch=vservice.searchfood(keyword);
		foodsearch.stream().forEach(flist->System.out.println(flist.getItemid()+"\t"+flist.getItemname()));
		return new ResponseEntity<List<VendorDetails>>(foodsearch,new HttpHeaders(),HttpStatus.OK);
	}
	
	
}
