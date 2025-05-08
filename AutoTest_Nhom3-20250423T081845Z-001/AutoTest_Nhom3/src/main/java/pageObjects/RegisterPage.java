package pageObjects;


import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import common.Constant;


public class RegisterPage extends GeneralPage {
    private final By inputEmail = By.id("email");
    private final By inputPassword = By.id("password");
    private final By inputConfirmPassword = By.id("confirmPassword");
    private final By inputPID = By.id("pid");
    private final By buttonRegister = By.xpath("//input[@value='Register']");
    private final By confirmationMessage = By.xpath("//p[text()='Registration Confirmed! You can now log in to the site.']");
    private final By errorMessage = By.xpath("//p[@class='message error']");
    private final By passwordErrorMessage = By.xpath("//label[@class='validation-error' and contains(text(), 'Invalid password length')]");
    private final By pidErrorMessage = By.xpath("//label[@class='validation-error' and contains(text(), 'Invalid ID length')]");



    public void enterEmail(String email) {
        WebElement emailField = Constant.WEBDRIVER.findElement(inputEmail);
        emailField.clear();
        emailField.sendKeys(email);
    }


    public void enterPassword(String password) {
        WebElement passwordField = Constant.WEBDRIVER.findElement(inputPassword);
        passwordField.clear();
        passwordField.sendKeys(password);
    }


    public void enterConfirmPassword(String confirmPassword) {
        WebElement confirmPasswordField = Constant.WEBDRIVER.findElement(inputConfirmPassword);
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(confirmPassword);
    }


    public void enterPID(String PID) {
        WebElement pidField = Constant.WEBDRIVER.findElement(inputPID);
        pidField.clear();
        pidField.sendKeys(PID);
    }


    public void clickRegisterButton() {
        WebElement registerButton = Constant.WEBDRIVER.findElement(buttonRegister);
        registerButton.click();
    }


//    public String getConfirmationMessage() {
//        return Constant.WEBDRIVER.findElement(confirmationMessage).getText();
//    }


    public String getErrorMessage() {
        WebElement errorMessageElement = Constant.WEBDRIVER.findElement(errorMessage);
        return errorMessageElement.getText();
    }

    public String getPasswordErrorMessage() {
        WebElement passwordErrorElement = Constant.WEBDRIVER.findElement(passwordErrorMessage);
        return passwordErrorElement.getText();
    }


    public String getPidErrorMessage() {
        WebElement pidErrorElement = Constant.WEBDRIVER.findElement(pidErrorMessage);
        return pidErrorElement.getText();
    }

    public RegisterPage register(String email, String password, String confirmPassword, String PID) {
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        enterPID(PID);
        clickRegisterButton();
        return this;
    }
    public String getConfirmationMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
            WebElement confirmationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage));
            return confirmationElement.getText().trim(); // Thêm .trim() để loại bỏ khoảng trắng thừa
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Không tìm thấy hoặc thông báo xác nhận không hiển thị sau 10 giây.");
            return "";
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.err.println("Không tìm thấy phần tử thông báo xác nhận.");
            return "";
        }
    }
}
