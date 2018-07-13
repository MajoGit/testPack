package otpSeleniumPckg;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;

public class CallOtp {

	static final String OTP_URL = "https://www.otpbanka.sk/otp-hypo-uver";
	static final String NAME = "Jozef";
	static final String SURNAME = "Mrkvicka";
	static final String PHONE_NUMBER = "0903111999";
	static final String EMAIL = "jozko.mrkvicka@gmail.com";
	static final String EL_NAME_NAME = "fieldId-2704-name";
	static final String EL_NAME_SURNAME = "fieldId-2707-surname";
	static final String EL_NAME_PHONE = "fieldId-2710-phone";
	static final String EL_NAME_EMAIL = "fieldId-2713-email";
	static final String BRANCH_OFFICE = "Bratislava - Polus";

	static void fillElement(String value, String elementName, WebDriver driver) {
		WebElement element = driver.findElement(By.name(elementName));
		element.sendKeys(value);
	}

	static void shiftSlider(WebDriver driver) throws InterruptedException {
		WebElement slidebar = driver.findElement(By.className("bgSlider"));
		int slidebarLength = slidebar.getSize().getWidth();
		WebElement handle = driver.findElement(By.xpath("//*[contains(@id, 'slider')]"));
		Actions act = new Actions(driver);
		act.clickAndHold(handle).moveByOffset(slidebarLength, 0).release().build().perform();
	}

	static void pickBranchOffice(WebDriver driver) throws InterruptedException {
		WebElement outer = driver.findElement(By.xpath("//*[contains(@class, 'selectize-control')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", outer);
		Thread.sleep(500);
		WebElement dropDown = driver.findElement(By.xpath("//*[contains(@class, 'selectize-input')]"));
		dropDown.click();

		WebElement pickList = driver.findElement(By.className("selectize-dropdown-content"));

		List<WebElement> elList = pickList.findElements(By.className("option"));
		if (elList != null && !elList.isEmpty()) {
			for (int i = 0; i < elList.size(); i++) {
				if (BRANCH_OFFICE.equals(elList.get(i).getText())) {
					elList.get(i).click();
					break;
				}
			}
		}

	}

	static void confirmButton(WebDriver driver) {
		WebElement confirmButton = driver.findElement(By.xpath("//input[@name='condition'][@type='checkbox']"));
		confirmButton.click();
	}

	public static void main(String[] args) throws InterruptedException {

		WebDriver driver = new FirefoxDriver();
		driver.get(OTP_URL);
		driver.manage().window().maximize();

		fillElement(NAME, EL_NAME_NAME, driver);
		fillElement(SURNAME, EL_NAME_SURNAME, driver);
		fillElement(PHONE_NUMBER, EL_NAME_PHONE, driver);
		fillElement(EMAIL, EL_NAME_EMAIL, driver);
		pickBranchOffice(driver);
		confirmButton(driver);
		shiftSlider(driver);

	}

}
