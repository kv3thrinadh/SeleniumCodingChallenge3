package codingChallenge;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Part3 {
	
	@Test
	public void findOuthighestPrice() throws InterruptedException, ParseException
	{
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		
		driver.get("https://www.saucedemo.com/");
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();
		driver.findElement(By.xpath("//*[text()='Products']")).isDisplayed();
		List <WebElement> princes = driver.findElements(By.xpath("//*[@class='inventory_item_price']"));
		ArrayList <Double>priceList = new ArrayList<Double>();
		for(WebElement price :princes ) {
			priceList.add(Double.parseDouble(price.getText().replace("$", "").replace(",", "")));

		}

		//Finding out the maximum value 
		double max = priceList.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
		System.out.println("Maximum value : " + max);
		
		//Adding to cart
		driver.findElement(By.xpath("//*[text()='"+max+"' and @class = 'inventory_item_price']/following-sibling::button")).click();
		String titleofTheproductOnproductListScreen = driver.findElement(By.xpath("//*[text()='"+max+"' and @class = 'inventory_item_price']/parent::div/preceding-sibling::div//*[@class='inventory_item_name']")).getText();
		
		//Clicking on Cart
		driver.findElement(By.xpath("//*[@class='shopping_cart_link']")).click();
		
		String titleofTheproductOnCart = driver.findElement(By.xpath("//*[@class='inventory_item_name']")).getText();
		Assert.assertEquals(titleofTheproductOnproductListScreen, titleofTheproductOnCart);
		
		String priceOnCart = driver.findElement(By.xpath("//*[@class='inventory_item_price']")).getText();
		Assert.assertEquals("$"+max,priceOnCart);
		driver.quit();
		
	}
}
