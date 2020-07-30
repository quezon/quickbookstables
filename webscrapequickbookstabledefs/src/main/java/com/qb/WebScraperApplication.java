package com.qb;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.qb.model.QBVersion;
import com.qb.ops.WebScrapping;
import com.qb.repo.QBVersionRepo;

@SpringBootApplication
@ComponentScan(basePackages = {"com.qb"})
public class WebScraperApplication implements CommandLineRunner {
	@Autowired
	QBVersionRepo qbvrepo;
	
	private QBVersion qb = new QBVersion();
	//xpath for tables
	private String xpathOfOneBigTable = "//*[@id='scorable_table']/table";
	//xpath for tablerows
	// //*[@class='scorable_table'][position() < 3]/table/tbody
	// //*[@class='scorable_table']/table/tbody
	private String xpathOfTableNames = "//*[@color='blue']";
	private String xpathOfTables = "//*[@class='scorable_table'][position() >= 1]/table/tbody";
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(WebScraperApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Map<String,String> oneTableMap = new HashMap<String, String>();
		Map<String,String> multipleTableMap = new HashMap<String, String>();
		
		oneTableMap.put("tables", xpathOfOneBigTable);
		multipleTableMap.put("names", xpathOfTableNames);
		multipleTableMap.put("tables", xpathOfTables);
		
		qb.setVersion("usa");
		
		// perform web crawling here and create a list of qbtables
		new WebScrapping(qb, 1, oneTableMap, "https://doc.qodbc.com/qodbc/usa/TableList.php?categoryName=");
		// perform web crawling here and create a list of qbtablerows
		new WebScrapping(qb, 2, multipleTableMap, "https://doc.qodbc.com/qodbc/usa/TableDetails.php?tableName=All"); 
		qbvrepo.save(qb);
	}
}
