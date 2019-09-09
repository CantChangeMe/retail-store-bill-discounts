package com.vivek.retail.model.user;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.vivek.retail.app.constants.UserType;
import com.vivek.retail.model.user.User;

public class UserTest {
    
    private Date today;
    private User user;

    @Before
    public void setUp() throws Exception {
        today = new Date();
        user = new User(today, UserType.EMPLOYEE);
    }

    @Test
    public void testUserDateUserType() {
        assertEquals(today, user.getCustomerSince());
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        today.setTime(calendar.getTime().getTime());
        
        assertFalse(today.equals(user.getCustomerSince()));
        
    }
    
}
