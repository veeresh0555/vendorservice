package com.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vendor.model.VendorDetails;

@Repository
public interface VendorRepository extends JpaRepository<VendorDetails, Long> {

	
	@Query(value="select * from vendor v where v.vname like %:keyword% OR v.itemname like %:keyword% OR v.itmtype like %:keyword%",nativeQuery = true)
	public List<VendorDetails> searchBykeyword(@Param("keyword") String keyword);
	
	//@Query("select v from VendorDetails v where v.vname like % :vname % OR v.itemname like % :itemname % OR v.itmtype like % :itmtype %")
	//public List<VendorDetails> searchBykeyword(@Param("vname") String vname,@Param("itemname") String itemname,@Param("itmtype") String itmtype);
	
}
