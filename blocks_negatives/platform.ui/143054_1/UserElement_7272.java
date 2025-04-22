        return getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    /**
     * The <code>Userment</code> implementation of this
     * <code>IPropertySource</code> method returns the following properties
     *
     *	1) P_ADDRESS returns Address (IPropertySource), the address of this user
     * 	2) P_FULLNAME returns Name (IPropertySource), the full name of this user
     *  3) P_PHONENUMBER returns String, the phone number of this user
     *  4) P_EMAIL returns EmailAddress (IPropertySource), the email address of this user
     *  5) P_COOP returns Boolean, whether the individual is a coop student or not
     *  6) P_BDAY returns Birthday
     *  7) P_SALARY return java.lang.Float
     *  8) P_HAIRCOLOR, expects RGB
     *  9) P_EYECOLOR, expects RGB
     */
    @Override
