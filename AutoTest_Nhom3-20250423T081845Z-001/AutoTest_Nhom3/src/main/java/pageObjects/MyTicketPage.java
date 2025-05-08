package pageObjects;


import common.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
public class MyTicketPage {




    private final By btnCancelTicket = By.xpath("//input[@value='Cancel']");
    private final By ticketList = By.xpath("//div[@class='DivTable']");


    public boolean isMyTicketPageDisplayed() {
        String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
        return currentUrl.contains("http://railwayb1.somee.com/Page/ManageTicket.cshtml");
    }
    // Method để hủy vé
    public void cancelTicket() {
        WebElement cancelButton = Constant.WEBDRIVER.findElement(By.xpath("//input[@value='Cancel']"));
        // Dùng JavaScript để click thay vì click thông thường
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].click();", cancelButton);

        // Xử lý alert nếu có
        Constant.WEBDRIVER.switchTo().alert().accept();
    }



    public boolean isTicketDisplayed() {
        return Constant.WEBDRIVER.findElements(ticketList).size() > 0;
    }
}



