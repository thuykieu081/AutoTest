package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import common.Constant;

public class PasswordResetPage extends GeneralPage {

    // Locators - Định vị các phần tử trên trang
    private final By txtNewPassword = By.id("newPassword");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By txtResetToken = By.id("resetToken");
    private final By btnResetPassword = By.xpath("//input[@value='Reset Password']");
    private final By lblErrorMessage = By.xpath("//div[@class='message error']");
    private final By lblTokenFieldError = By.xpath("//label[@for='resetToken']/following-sibling::label[@class='validation-error']");

    // Methods - Các phương thức tương tác với trang
    public void open(String token) {
        // Điều hướng trực tiếp đến trang đặt lại mật khẩu với token đã cho
        Constant.WEBDRIVER.get(Constant.RAILWAY_URL + "/Account/ResetPassword.cshtml?token=" + token);
    }

    public void enterNewPassword(String password) {

        WebElement passwordElement = Constant.WEBDRIVER.findElement(txtNewPassword);
        passwordElement.clear();
        passwordElement.sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        WebElement confirmPasswordElement = Constant.WEBDRIVER.findElement(txtConfirmPassword);
        confirmPasswordElement.clear();
        confirmPasswordElement.sendKeys(confirmPassword);
    }

    public void clearResetToken() {
        WebElement resetTokenElement = Constant.WEBDRIVER.findElement(txtResetToken);
        resetTokenElement.clear();
    }

    public void clickResetPasswordButton() {
        Constant.WEBDRIVER.findElement(btnResetPassword).click();
    }

    public String getErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblErrorMessage).getText();
    }

    public String getTokenFieldErrorMessage() {
        return Constant.WEBDRIVER.findElement(lblTokenFieldError).getText();
    }
    public String getConfirmPasswordFieldErrorMessage() {
        WebElement errorElement = Constant.WEBDRIVER.findElement(By.id("confirmPassword-error"));
        return errorElement.getText();
    }
}