package pageObjects;
import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class HomePage extends  GeneralPage{
    // Locators
    private final By tabBookTicket = By.xpath("//a[@href='/Page/BookTicketPage.cshtml']");
    private final By tabMyTicket = By.xpath("//a[@href='/Page/ManageTicket.cshtml']");
//    private final By tabChangePassword = By.xpath("//a[@href='/Account/ChangePassword.cshtml']");
    private final By tabChangePassword = By.xpath("//a[@href='/Account/ChangePassword.cshtml']");
    private final By tabLogout = By.xpath("//div[@id='menu']//a[@href='/Account/Logout']");
    private final By tabRegister = By.xpath("//a[@href='/Account/Register.cshtml']"); // Locator cho trang đăng ký
    private final By tabTimetable = By.xpath("//a[@href='TrainTimeListPage.cshtml']"); // Locator cho ta

    // Elements
    // Method
    public HomePage open()
    {
        Constant.WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this;
    }


    public BookTicketPage gotoBookTicketPage() {
        Constant.WEBDRIVER.findElement(tabBookTicket).click();
        return new BookTicketPage();
    }


    public boolean isMyTicketTabDisplayed() {
        return Constant.WEBDRIVER.findElement(this.tabMyTicket).isDisplayed();
    }


    public boolean isChangePasswordTabDisplayed() {
        return Constant.WEBDRIVER.findElement(this.tabChangePassword).isDisplayed();
    }


    public boolean isLogoutTabDisplayed() {
        return Constant.WEBDRIVER.findElement(this.tabLogout).isDisplayed();
    }


    public MyTicketPage gotoMyTicketPage() {
        Constant.WEBDRIVER.findElement(this.tabMyTicket).click();
        return new MyTicketPage();
    }


    public ChangePasswordPage gotoChangePasswordPage() {
        Constant.WEBDRIVER.findElement(this.tabChangePassword).click();
        return new ChangePasswordPage();
    }

    public RegisterPage gotoRegisterPage() {
        Constant.WEBDRIVER.findElement(tabRegister).click();
        return new RegisterPage();
    }


    public TimetablePage gotoTimetablePage() {
        Constant.WEBDRIVER.findElement(tabTimetable).click();
        return new TimetablePage();
    }
    private final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong"); // Ví dụ locator - CẦN CẬP NHẬT

    public String getWelcomeMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
            WebElement welcomeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(lblWelcomeMessage));
            return welcomeElement.getText().trim();
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Không tìm thấy hoặc phần tử chào mừng không hiển thị sau 10 giây.");
            return "";
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.err.println("Không tìm thấy phần tử chào mừng.");
            return "";
        }
    }
}





