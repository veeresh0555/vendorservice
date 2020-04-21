package com.vendor.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.vendor.model.VendorDetails;

@Configuration
@EnableBatchProcessing
public class SbatchConfiguration {

	@Autowired
	private JobBuilderFactory jbf;
	
	@Autowired
	private StepBuilderFactory sbf;
	
	@Autowired
	private DataSource datasource;
	
	@Bean
	public LineMapper<VendorDetails> lineMapper(){
		DefaultLineMapper<VendorDetails> linemapper=new DefaultLineMapper<>();
		DelimitedLineTokenizer linetokenizer=new DelimitedLineTokenizer();
		BeanWrapperFieldSetMapper<VendorDetails> bfsMapper=new BeanWrapperFieldSetMapper<>();
		linetokenizer.setNames(new String[] {"sno","vname","vcity","vlocation","itemid","itemname","itype","price"});
		linetokenizer.setIncludedFields(new int[] {0,1,2,3,4,5,6,7});
		 bfsMapper.setTargetType(VendorDetails.class);
		linemapper.setLineTokenizer(linetokenizer);//set lineTokenizer----
		linemapper.setFieldSetMapper(bfsMapper); //set bfsMapper--- to the linemapper
		return linemapper;
	}
	
	@Bean
	public FlatFileItemReader<VendorDetails> reader(){
		FlatFileItemReader<VendorDetails> itemReader=new FlatFileItemReader<>();
		itemReader.setResource(new ClassPathResource("vendor1.csv"));
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}
	
	@Bean
	public JdbcBatchItemWriter<VendorDetails> writer(){
		JdbcBatchItemWriter<VendorDetails> itemWriter=new JdbcBatchItemWriter<>();
		itemWriter.setDataSource(datasource);
		itemWriter.setSql("insert into vendor (id,vname,vcity,vlocation,itemid,itemname,itype,price) values(:id, :vname, :vcity, :vlocation, :itemid, :itemname, :itype, :price)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<VendorDetails>());
		return itemWriter;
	}
	
	@Bean
	public Step step1() {
		return sbf.get("step1").<VendorDetails,VendorDetails>chunk(10)
				.reader(reader())
				//.processor(function)--for logs enable then work
				.writer(writer()).build();
	}
	@Bean
	public Job readvendorcsvfile() {
		return jbf.get("readvendorcsvfile").incrementer(new RunIdIncrementer()).start(step1()).build();
	}
	
	
}
