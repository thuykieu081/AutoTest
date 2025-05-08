package pageObjects;
import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class LoginPage extends GeneralPage{
    //Locators
    private final By _txtUsername = By.xpath("//input[@id='username']");
    private final By _txtPassword = By.xpath("//input[@id='password']");
    private final By _btnLogin = By.xpath("//input[@value='login']");
    private final By _lblLoginErrorMsg = By.xpath("//p[@class='message error LoginForm']");
    private final By _lnkForgotPassword = By.xpath("//a[@href='/Account/ForgotPassword.cshtml']");


    //Elements
    public WebElement getTxtUsername()
    {
        return Constant.WEBDRIVER.findElement(_txtUsername);
    }


    public WebElement getTxtPassword()
    {
        return Constant.WEBDRIVER.findElement(_txtPassword);
    }


    public WebElement getBtnLogin()
    {
        return Constant.WEBDRIVER.findElement(_btnLogin);
    }


    public WebElement getLnlLoginErrorMsg()
    {
        return Constant.WEBDRIVER.findElement(_lblLoginErrorMsg);
    }


    public WebElement getLnkForgotPassword() {
        return Constant.WEBDRIVER.findElement(_lnkForgotPassword);
    }






    //Methods
    public HomePage login(String username, String password)
    {
        this.getTxtUsername().sendKeys(username);
        this.getTxtPassword().sendKeys(password);

        WebElement loginButton = this.getBtnLogin();

        // Cuộn đến nút Login
        ((org.openqa.selenium.JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].scrollIntoView(true);", loginButton);

        // Đợi cho đến khi có thể click được
        new org.openqa.selenium.support.ui.WebDriverWait(Constant.WEBDRIVER, java.time.Duration.ofSeconds(10))
                .until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(loginButton));

        // Click bằng JavaScript để tránh lỗi bị che
        ((org.openqa.selenium.JavascriptExecutor) Constant.WEBDRIVER)
                .executeScript("arguments[0].click();", loginButton);

        return new HomePage();
    }



    public String getErrorMessage() {
        return this.getLnlLoginErrorMsg().getText();
    }


    public ForgotPasswordPage gotoForgotPasswordPage() {
        this.getLnkForgotPassword().click();
        return new ForgotPasswordPage();
    }


}




