package pageObjects;

import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ForgotPasswordPage extends GeneralPage {
    // Locators
    private final By _txtEmail = By.id("email"); // Giữ nguyên nếu ID này đúng
    private final By _btnSendInstructions = By.xpath("//input[@value='Send Instructions']"); // **CẬP NHẬT LOCATOR**


    // Elements
    public WebElement getTxtEmail() {
        return Constant.WEBDRIVER.findElement(_txtEmail);
    }


    public WebElement getBtnSendInstructions() {
        return Constant.WEBDRIVER.findElement(_btnSendInstructions);
    }


    // Methods
    public void enterEmail(String email) {
        this.getTxtEmail().clear();
        this.getTxtEmail().sendKeys(email);
    }


    public void clickSendInstructionsButton() {
        this.getBtnSendInstructions().click();
    }
}