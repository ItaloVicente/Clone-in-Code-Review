        return this.toString();
    }

    /**
     * Returns the email address
     */
    EmailAddress getEmailAddress() {
        if (emailAddress == null)
            emailAddress = new EmailAddress();
        return emailAddress;
    }

    /**
     * Returns the eye color
     */
    private RGB getEyeColor() {
        if (eyeColor == null)
            eyeColor = new RGB(60, 60, 60);
        return eyeColor;
    }

    /**
     * Returns the full name
     */
    private Name getFullName() {
        if (fullName == null)
            fullName = new Name(getName());
        return fullName;
    }

    /**
     * Returns the hair color
     */
    private RGB getHairColor() {
        if (hairColor == null)
            hairColor = new RGB(255, 255, 255);
        return hairColor;
    }

    /**
     * Returns the phone number
     */
    private String getPhoneNumber() {
        return phoneNumber;
    }

    /* (non-Javadoc)
     * Method declared on IPropertySource
     */
    @Override
