        if (property.equals(P_ID_ADDRESS)) {
            setAddress(address_Default);
            return;
        }
        if (property.equals(P_ID_FULLNAME)) {
            setFullName(fullName_Default);
            return;
        }
        if (property.equals(P_ID_PHONENUMBER)) {
            setPhoneNumber(phoneNumber_Default);
            return;
        }
        if (property.equals(P_ID_EMAIL)) {
            setEmailAddress(emailAddress_Default);
            return;
        }
        if (property.equals(P_ID_COOP)) {
            setCoop(coop_Default);
        }
        if (property.equals(P_ID_BDAY)) {
            setBirthday(birthday_Default);
            return;
        }
        if (property.equals(P_ID_SALARY)) {
            setSalary(salary_Default);
            return;
        }
        if (property.equals(P_ID_HAIRCOLOR)) {
            setHairColor(hairColor_Default);
            return;
        }
        if (property.equals(P_ID_EYECOLOR)) {
            setEyeColor(eyeColor_Default);
            return;
        }
        super.resetPropertyValue(property);
    }

    /**
     * Sets the address
     */
    public void setAddress(Address newAddress) {
        address = newAddress;
    }

    /**
     * Sets the birthday
     */
    public void setBirthday(Birthday newBirthday) {
        birthday = new Birthday();
    }

    /**
     * Sets the coop
     */
    public void setCoop(Boolean newCoop) {
        coop = newCoop;
    }

    /**
     *Sets the email address
     */
    public void setEmailAddress(EmailAddress newEmailAddress) {
        emailAddress = newEmailAddress;
    }

    /**
     * Sets eye color
     */
    public void setEyeColor(RGB newEyeColor) {
        eyeColor = newEyeColor;
    }

    /**
     * Sets full name
     */
    public void setFullName(Name newFullName) {
        fullName = newFullName;
    }

    /**
     * Sets hair color
     */
    public void setHairColor(RGB newHairColor) {
        hairColor = newHairColor;
    }

    /**
     * Sets phone number
     */
    public void setPhoneNumber(String newPhoneNumber) {
        phoneNumber = newPhoneNumber;
    }

    /**
     * The <code>OrganizationElement</code> implementation of this
     * <code>IPropertySource</code> method
     * defines the following Setable properties
     *
     *	1) P_ADDRESS, expects Address
     *	2) P_FULLNAME, expects Name
     *	3) P_PHONENUMBER, expects String
     *  4) P_EMAIL, expects EmailAddress.
     *  5) P_BOOLEAN, expects Boolean, whether the individual is a coop student or not
     *  6) P_BDAY, expects Birthday
     *  7) P_SALARY, expects java.lang.Float
     *  8) P_HAIRCOLOR, expects RGB
     *  9) P_EYECOLOR, expects RGB
     */
    @Override
