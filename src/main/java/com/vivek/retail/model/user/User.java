package com.vivek.retail.model.user;

import java.util.Date;

import com.vivek.retail.app.constants.UserType;

public class User {
    
    private Date customerSince;
    
    private UserType type;
   
    public User(Date customerSince, UserType type) {
        super();
        if(customerSince != null) {
            this.customerSince = new Date(customerSince.getTime());
        }
        this.type = type;
    }

    public Date getCustomerSince() {
        Date since = this.customerSince;
        if(this.customerSince != null) {
            since = new Date(customerSince.getTime());
        }
        return since;
    }

    public UserType getType() {
        return type;
    }

    public void setCustomerSince(Date customerSince) {
        
        if(customerSince != null) {
            this.customerSince = new Date(customerSince.getTime());
        } else {
            this.customerSince = null;
        }
    }

    public void setType(UserType type) {
        this.type = type;
    }
    
}
