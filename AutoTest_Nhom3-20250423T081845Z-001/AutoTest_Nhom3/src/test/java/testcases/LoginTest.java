package testcases;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import common.Constant;
import org.testng.annotations.Test;
import pageObjects.*;


public class LoginTest {
    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");


        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
    }


    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();


    }


    @Test
    public void TC01() {
        System.out.println("TC01 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        homePage.open();


        LoginPage loginPage = homePage.gotoLoginPage();


        String actualMsg = loginPage.login(Constant.USERNAME, Constant.PASSWORD).getWelcomeMessage();
        String expectedMsg = "Welcome " + Constant.USERNAME;


        Assert.assertEquals(actualMsg, expectedMsg, "Welcome message is not display as expected");
    }

    @Test
    public void TC02() {
        System.out.println("TC02 - User can't login with blank 'Username' textbox");
        HomePage homePage = new HomePage();
        homePage.open();


        LoginPage loginPage = homePage.gotoLoginPage();


        loginPage.login("", Constant.PASSWORD);


        String actualErrorMsg = loginPage.getErrorMessage();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form.";


        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected when 'Username' is blank");
    }

    @Test
    public void TC03() {
        System.out.println("TC03 - User cannot log into Railway with invalid password");

        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, "123");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String actualErrorMsg = loginPage.getErrorMessage();
        boolean isValidMessage = actualErrorMsg.equals("There was a problem with your login and/or errors exist in your form.");

        Assert.assertTrue(isValidMessage,
                "Error message is not displayed as expected when invalid password. Actual: " + actualErrorMsg);
    }

    @Test
    public void TC04() {
        System.out.println("TC04 - Login page displays when un-logged User clicks on 'Book ticket' tab");

        // Step 1: Navigate to QA Railway Website
        HomePage homePage = new HomePage();
        homePage.open();

       // Step 2: Click on "Book ticket" tab
        homePage.gotoBookTicketPage();


        String currentUrl = Constant.WEBDRIVER.getCurrentUrl();
        String expectedUrl = "http://railwayb1.somee.com/Account/Login.cshtml";


        Assert.assertTrue(currentUrl.startsWith(expectedUrl), "Login page is not displayed when un-logged user clicks on 'Book ticket' tab.");
    }


    @Test
    public void TC05() {
        System.out.println("TC05 - System shows warning after 4 failed login attempts");

        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();

        // Thực hiện 4 lần đăng nhập sai
        for (int i = 1; i <= 4; i++) {
            loginPage.login(Constant.USERNAME, "wrongPassword");

            if (i < 4) {
                String actualMsg = loginPage.getErrorMessage();
                String expectedMsg = "Invalid username or password. Please try again.";
                Assert.assertEquals(actualMsg, expectedMsg, "Error message not as expected on attempt " + i);
            }
        }

        // Lần thứ 4, kiểm tra cảnh báo đặc biệt xuất hiện
        String actualWarningMsg = loginPage.getErrorMessage();
        String expectedWarningMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
        Assert.assertEquals(actualWarningMsg, expectedWarningMsg, "Warning message after 4 failed login attempts is incorrect.");
    }


    @Test
    public void TC06() {
        System.out.println("TC06 - Additional pages display once user logged in");


        HomePage homePage = new HomePage();
        homePage.open();


        LoginPage loginPage = homePage.gotoLoginPage();


        loginPage.login("Hong123@gmail.com", "Hong12345678");


        Assert.assertTrue(homePage.isMyTicketTabDisplayed(), "My Ticket tab is not displayed");
        Assert.assertTrue(homePage.isChangePasswordTabDisplayed(), "Change Password tab is not displayed");
        Assert.assertTrue(homePage.isLogoutTabDisplayed(), "Logout tab is not displayed");


        MyTicketPage myTicketPage = homePage.gotoMyTicketPage();
        Assert.assertTrue(myTicketPage.isMyTicketPageDisplayed(), "User  is not directed to My Ticket page");


        ChangePasswordPage changePasswordPage = homePage.gotoChangePasswordPage();
        Assert.assertTrue(changePasswordPage.isChangePasswordPageDisplayed(), "User is not directed to Change Password page");
    }

    @Test
    public void TC07() {
        System.out.println("TC07 - User can create new account");


        HomePage homePage = new HomePage();
        homePage.open();


        RegisterPage registerPage = homePage.gotoRegisterPage();


        String email = "newnewemail1300@email.com";
        String password = "Password123";
        String confirmPassword = "Password123";
        String PID = "12345678945";
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.enterConfirmPassword(confirmPassword);
        registerPage.enterPID(PID);


        registerPage.clickRegisterButton();


        String expectedMessage = "Thank you for registering your account";
        String actualMessage = registerPage.getConfirmationMessage();
        Assert.assertEquals(actualMessage, expectedMessage, "Confirmation message is not displayed as expected");
    }

    @Test
    public void TC08() {
        System.out.println("TC08 - User can't login with an account hasn't been activated");


        HomePage homePage = new HomePage();
        homePage.open();


        LoginPage loginPage = homePage.gotoLoginPage();


        String unactivatedUsername = "unactivated_user@gmail.com";
        String password = "Password123";


        loginPage.login(unactivatedUsername, password);


        String actualErrorMsg = loginPage.getErrorMessage();
        String expectedErrorMsg = "Invalid username or password. Please try again.";


        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected for unactivated account");
    }

    @Test
    public void TC09() {
        System.out.println("TC09 - User can change password");


        HomePage homePage = new HomePage();
        homePage.open();


        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("phamtrandieukhanh2004@gmail.com", "dieukhanh.com2");


        ChangePasswordPage changePasswordPage = homePage.gotoChangePasswordPage();


        String currentPassword = "dieukhanh.com2";
        String newPassword = "dieukhanh.com";
        String confirmPassword = "dieukhanh.com";


        changePasswordPage.enterCurrentPassword(currentPassword);
        changePasswordPage.enterNewPassword(newPassword);
        changePasswordPage.enterConfirmPassword(confirmPassword);


        changePasswordPage.clickChangePasswordButton();


        String actualMessage = changePasswordPage.getConfirmationMessage();
        String expectedMessage = "Your password has been updated!";


        Assert.assertEquals(actualMessage, expectedMessage, "Confirmation message is not displayed as expected");
    }

    @Test
    public void TC10() {
        System.out.println("TC10 - User can't create account with 'Confirm password' is not the same with 'Password'");


        HomePage homePage = new HomePage();
        homePage.open();


        RegisterPage registerPage = homePage.gotoRegisterPage();


        String email = "newuser@gmail.com";
        String password = "Password123";
        String confirmPassword = "DifferentPassword";
        String PID = "123456789";


        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        registerPage.enterConfirmPassword(confirmPassword);
        registerPage.enterPID(PID);


        registerPage.clickRegisterButton();


        String actualErrorMessage = registerPage.getErrorMessage();
        String expectedErrorMessage = "There're errors in the form. Please correct the errors and try again.";


        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message is not displayed as expected when passwords are not the same");
    }


    @Test
    public void TC11() {
        System.out.println("TC11 - User can't create account while password and PID fields are empty");

        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegisterPage();

        String email = "test123@gmail.com";
        String password = "";
        String confirmPassword = "";
        String pidPassport = "";

        registerPage.register(email, password, confirmPassword, pidPassport);

        String actualErrorMessage = registerPage.getErrorMessage();
        String expectedErrorMessage = "There're errors in the form. Please correct the errors and try again.";
        Assert.assertEquals(actualErrorMessage.trim(), expectedErrorMessage.trim(), "Error message is not displayed as expected");


        String actualPasswordError = registerPage.getPasswordErrorMessage();
        String expectedPasswordError = "Invalid password length";
        Assert.assertEquals(actualPasswordError.trim(), expectedPasswordError.trim(), "Password error message is not displayed as expected");

        String actualPidError = registerPage.getPidErrorMessage();
        String expectedPidError = "Invalid ID length";
        Assert.assertEquals(actualPidError.trim(), expectedPidError.trim(), "PID error message is not displayed as expected");

        System.out.println("TC11 - Error message displayed correctly when password and PID are empty");
    }
    @Test
    public void TC12() {
        System.out.println("TC12 - Errors display when password reset token is blank");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        ForgotPasswordPage forgotPasswordPage = loginPage.gotoForgotPasswordPage();

        forgotPasswordPage.enterEmail(Constant.USERNAME);
        forgotPasswordPage.clickSendInstructionsButton();

        // Mô phỏng vì  không thể tự động kiểm tra email
        PasswordResetPage passwordResetPage = new PasswordResetPage();
        passwordResetPage.open("");

        passwordResetPage.enterNewPassword("newpassword123");
        passwordResetPage.enterConfirmPassword("newpassword123");
        passwordResetPage.clearResetToken();

        passwordResetPage.clickResetPasswordButton();

        String actualErrorMsg = passwordResetPage.getErrorMessage();
        String expectedErrorMsg = "The password reset token is incorrect or may be expired. Visit the forgot password page to generate a new one.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Thông báo lỗi không hiển thị như mong đợi phía trên biểu mẫu");

        String actualTokenErrorMsg = passwordResetPage.getTokenFieldErrorMessage();
        String expectedTokenErrorMsg = "The password reset token is invalid.";
        Assert.assertEquals(actualTokenErrorMsg, expectedTokenErrorMsg, "Thông báo lỗi không hiển thị như mong đợi bên cạnh trường 'Password Reset Token'");
    }

    @Test
    public void TC13() {
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        ForgotPasswordPage forgotPasswordPage = loginPage.gotoForgotPasswordPage();
        forgotPasswordPage.enterEmail(Constant.USERNAME);
        forgotPasswordPage.clickSendInstructionsButton();

        // Mô phỏng vì  không thể tự động kiểm tra email
        PasswordResetPage passwordResetPage = new PasswordResetPage();
        passwordResetPage.open("valid-mock-token");

        passwordResetPage.enterNewPassword("newpassword123");
        passwordResetPage.enterConfirmPassword("differentpassword123");

        passwordResetPage.clickResetPasswordButton();

        String actualErrorMsg = passwordResetPage.getErrorMessage();
        String expectedErrorMsg = "Could not reset password. Please correct the errors and try again.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Thông báo lỗi không hiển thị như mong đợi phía trên biểu mẫu");

        String actualConfirmPasswordErrorMsg = passwordResetPage.getConfirmPasswordFieldErrorMessage();
        String expectedConfirmPasswordErrorMsg = "The password confirmation did not match the new password.";
        Assert.assertEquals(actualConfirmPasswordErrorMsg, expectedConfirmPasswordErrorMsg, "Thông báo lỗi không hiển thị như mong đợi bên cạnh trường 'Confirm Password'");
    }

    @Test
    public void TC14() {
        System.out.println("TC14 - User can book 1 ticket at a time");


        HomePage homePage = new HomePage();
        homePage.open();


        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);


        BookTicketPage ticketBookingPage = homePage.gotoBookTicketPage();


        ticketBookingPage.bookTicket("4/30/2025","Sài Gòn","Nha Trang","Soft bed with air conditioner",1);


        String actualMsg = ticketBookingPage.getSuccessMessage();
        String expectedMsg = "Ticket booked successfully!";
        Assert.assertEquals(actualMsg.trim(), expectedMsg.trim(), "Success message is not displayed as expected.");


        TicketInfo ticketInfo = ticketBookingPage.getTicketInfo();
        Assert.assertEquals(ticketInfo.getDepartDate(), "4/30/2025", "Depart Date is not correct.");
        Assert.assertEquals(ticketInfo.getDepartStation(), "Sài Gòn", "Depart Station is not correct.");
        Assert.assertEquals(ticketInfo.getArriveStation(), "Nha Trang", "Arrive Station is not correct.");
        Assert.assertEquals(ticketInfo.getSeatType(), "Soft bed with air conditioner", "Seat Type is not correct.");
        Assert.assertEquals(ticketInfo.getAmount(), 1, "Ticket Amount is not correct.");
    }

    @Test
    public void TC15() {
        System.out.println("TC15 - User can open 'Book ticket' page by clicking on 'Book ticket' link in 'Train timetable' page");


        HomePage homePage = new HomePage();
        homePage.open();


        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);


        TimetablePage timetablePage = homePage.gotoTimetablePage();


        BookTicketPage bookTicketPage = timetablePage.clickBookTicketLink("Huế", "Sài Gòn");


        String expectedDepartFrom = "Huế";
        String expectedArriveAt = "Sài Gòn";


        Assert.assertEquals(bookTicketPage.getSelectedDepartStation(), expectedDepartFrom, "Departure from unexpected value");
        Assert.assertEquals(bookTicketPage.getSelectedArriveStation(), expectedArriveAt, "Arrive at value is not as expected");
    }

    @Test
    public void TC16() {
        System.out.println("TC16 - User can cancel a ticket");


        HomePage homePage = new HomePage();
        homePage.open();


        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);


        BookTicketPage bookTicketPage = homePage.gotoTimetablePage().clickBookTicketLink("Đà Nẵng", "Nha Trang");
        bookTicketPage.getBtnBookTicket();


        MyTicketPage myTicketPage = bookTicketPage.gotoMyTicketsPage();


        Assert.assertTrue(myTicketPage.isMyTicketPageDisplayed(), "My Ticket page is not displayed.");


        myTicketPage.cancelTicket();


        Assert.assertTrue(myTicketPage.isTicketDisplayed(), "The canceled ticket is still displayed.");
    }
}
