package com.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebScraper {
	public WebDriver driver = new FirefoxDriver();
	
	public void openTestSite() {
		driver.navigate().to("http://testing-ground.scraping.pro/login");
	}
	
	public void login(String username, String password) {
		WebElement username_editbox = driver.findElement(By.id("usr"));
		WebElement password_editbox = driver.findElement(By.id("pwd")) ;
		WebElement submit_button = driver.findElement(By.xpath("//input[@value='Login']"));
		
		username_editbox.sendKeys(username);
		password_editbox.sendKeys(password);
		submit_button.click();
	}
	
	public void getText() throws IOException {
		String text = 
				driver.findElement(By.xpath("//div[@id='case_login']/h3")).getText();
		Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("status.txt"),"utf-8"));
		writer.write(text);
		writer.close();
	}
	
	public void saveScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("screenshot.png"));
	}
	
	public void closeBrowser() {
		driver.close();
	}
	
	public static void main(String[] args) throws IOException {
		WebScraper webSrcapper = new WebScraper();
		webSrcapper.openTestSite();
		webSrcapper.login("admin", "12345");
		webSrcapper.getText();
		webSrcapper.saveScreenshot();
		webSrcapper.closeBrowser();
	}
}	
