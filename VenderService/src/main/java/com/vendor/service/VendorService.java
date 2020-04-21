package com.vendor.service;

import java.util.List;

import com.vendor.model.VendorDetails;

public interface VendorService {

	public List<VendorDetails> searchfood(String keyword);

}
