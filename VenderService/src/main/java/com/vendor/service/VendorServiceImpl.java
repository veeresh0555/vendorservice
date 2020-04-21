package com.vendor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendor.model.VendorDetails;
import com.vendor.repository.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

	
	@Autowired
	VendorRepository vRepository;

	@Override
	public List<VendorDetails> searchfood(String keyword) {
		List<VendorDetails> fsearch=vRepository.searchBykeyword(keyword);
		return fsearch;
	}
	
	
}
