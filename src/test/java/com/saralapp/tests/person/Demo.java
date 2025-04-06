package com.saralapp.tests.person;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Demo {

    @DataProvider(name = "MyDataProvider")
    public Object[][] getDataProvider(){
        return new Object[][]{
                {"user1", "password1"},
                {"user2", "password2"},
                {"user3", "password3"},
        };
    }

    @Test(dataProvider = "MyDataProvider")
    public void testLogin(String userName, String password) {

        System.out.println("Username: " + userName + ", Password: " + password);

    }
}
