package com.qb.ops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.qb.model.QBTable;
import com.qb.model.QBTableRelations;
import com.qb.model.QBTableRow;
import com.qb.model.QBVersion;

public class WebScrapping {
	private QBVersion qb;
	Map<String,String> xpaths;
	private List<WebElement> tableNames;
	private List<WebElement> htmlTables;
	private WebElement oneBigHTMLTable;
	//private static FirefoxOptions options = new FirefoxOptions();
	private WebDriver driver = new FirefoxDriver();

	
	public WebScrapping(QBVersion qb, int mode, Map<String,String> xpaths, String url) {
		int[] nums = {0,0,0};
		this.qb = qb;
		this.xpaths = xpaths;
		
		
		openSite(url);
		switch(mode) {
			case 1:
				nums[0] = 0; nums[1] = 1; nums[2] = 2;
				getHTMLTable(xpaths, nums, mode, null);
				break;
			case 2:
				getHTMLTables(xpaths);
				break;
		}
		
		closeBrowser();
	}
	
	public WebScrapping(QBVersion qb, int mode, Map<String,String> xpaths, String url, QBTable qbTable) {
		int[] nums = {0,0,0};
		this.qb = qb;
		this.xpaths = xpaths;
		
		openTab(url);
		switch(mode) {
			case 3:
				nums[0] = 1; nums[1] = 3; nums[2] = 4;
				getHTMLTable(xpaths, nums, mode, qbTable);
				break;
		}
		
		closeCurrentTabSwitchToFirstTab();
	}
	
	public void openSite(String url) {
		driver.navigate().to(url);
	}
	
	public void openTab(String url) {
		driver.findElement(By.cssSelector("Body")).sendKeys(Keys.CONTROL + "t");
		driver.navigate().to(url);
	}
	
	public void closeBrowser() {
		driver.close();
	}
	
	public void closeCurrentTabSwitchToFirstTab() {
//		Set<String> handlesSet = driver.getWindowHandles();
//        List<String> handlesList = new ArrayList<String>(handlesSet);
//        driver.switchTo().window(handlesList.get(0));
        driver.close();
//		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
	}
	
	public boolean getHTMLTables(Map<String,String> xpaths) {
		this.htmlTables = driver.findElements(By.xpath(xpaths.get("tables")));
		this.tableNames = driver.findElements(By.xpath(xpaths.get("names")));
		int size = this.htmlTables.size();
		
		System.out.println("htmltables size "+size);
		
		for(int i = 0; i < size; i++) {
			List<WebElement> trs = this.htmlTables.get(i).findElements(By.tagName("tr"));
			String tablename = this.tableNames.get(i).getText();
			List<QBTableRow> qbtablerows = new ArrayList<QBTableRow>();
			QBTable qbtable = this.qb.getQbTables().get(tablename);
			
			
			for(int j=1; j < trs.size(); j++){
				String fieldname = trs.get(j).findElement(By.xpath(".//td[1]")).getText();
				String typename = trs.get(j).findElement(By.xpath(".//td[3]")).getText();
				Integer length = Integer.valueOf(trs.get(j).findElement(By.xpath(".//td[4]")).getText());
				Boolean queryable = Boolean.valueOf(trs.get(j).findElement(By.xpath(".//td[5]")).getText());
				Boolean updateable = Boolean.valueOf(trs.get(j).findElement(By.xpath(".//td[6]")).getText());
				Boolean insertable = Boolean.valueOf(trs.get(j).findElement(By.xpath(".//td[7]")).getText());
				Boolean required = Boolean.valueOf(trs.get(j).findElement(By.xpath(".//td[8]")).getText());
				String validation = trs.get(j).findElement(By.xpath(".//td[9]")).getText();
				
				if (!validation.contains("\n")) {
					qbtablerows.add(new QBTableRowBuilder(qbtable)
						.qbtable(tablename)
						.fieldname(fieldname)
						.typename(typename)
						.length(length)
						.queryable(queryable)
						.updateable(updateable)
						.insertable(insertable)
						.required(required)
						.validate(validation)
						.build());
				} else {
					qbtablerows.add(new QBTableRowBuilder(qbtable)
						.qbtable(tablename)
						.fieldname(fieldname)
						.typename(typename)
						.length(length)
						.queryable(queryable)
						.updateable(updateable)
						.insertable(insertable)
						.required(required)
						.validate(validation.split("\n"))
						.build());
				}
			}
			
			
			this.qb.getQbTables().get(tablename).setQbtrows(qbtablerows);
			System.out.println(this.qb.getQbTables().get(tablename));
		}
		
		return true;
	}
	
	public void getHTMLTable(Map<String,String> xpaths, int[] columns, int mode, QBTable qbTable) {
		List<QBTableRelations> relations = new ArrayList<>(); 
		Map<String, QBTable> qbTables = new HashMap<>();
		this.oneBigHTMLTable = driver.findElement(By.xpath(xpaths.get("tables")));
		String xpath;
		
		if (mode == 1) {
			xpath = ".//tr[position() > 1]";
		} else {
			xpath = ".//tr[position() > 1]";
		}
		
		for(WebElement we: this.oneBigHTMLTable.findElements(By.xpath(xpath))) {
			List<WebElement> tds = we.findElements(By.tagName("td"));
			
			String col1 = tds.get(columns[0]).getText();
			String col2 = tds.get(columns[1]).getText();
			String col3 = tds.get(columns[2]).getText();
			System.out.println(col1 + " " + col2 + " " + col3);
			if (mode == 1) {
				QBTable qbTableLoc = new QBTable(col1,col2,col3); 
				qbTableLoc.setQbversion(this.qb);
				new WebScrapping(qb, 3, xpaths, "https://doc.qodbc.com/qodbc/usa/Relation.php?tableName="+col1, qbTableLoc);
				qbTables.put(col1, qbTableLoc);
			} else if (mode == 3) {
				QBTableRelations relation = new QBTableRelations(col1,col2,col3);
				relation.setQbt(qbTable);
				relations.add(relation);
			}
		}
		
		if (mode == 1) {
			this.qb.setQbTables(qbTables);
		} else if (mode == 3) {
			qbTable.setRelations(relations);
			System.out.println(relations.size());
		}
		
	}
}
