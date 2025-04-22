        return;
    }

    /**
     * Sets the domain
     */
    private void setDomain(java.lang.String newDomain) {
        domain = newDomain;
    }

    /**
     * Parses emailAddress into domain and userid. Throws SetPropertyVetoException
     * if emailAddress does not contain an userid and domain seperated by '@'.
     *
     * @param emailAddress the email address
     * @throws IllegalArgumentException
     */
    private void setEmailAddress(String emailAddress)
            throws IllegalArgumentException {
        if (emailAddress == null)
            throw new IllegalArgumentException(MessageUtil
        int index = emailAddress.indexOf('@');
        int length = emailAddress.length();
        if (index > 0 && index < length) {
            setUserid(emailAddress.substring(0, index));
            setDomain(emailAddress.substring(index + 1));
            return;
        }
        throw new IllegalArgumentException(
                MessageUtil
    }

    /**
     * The <code>Address</code> implementation of this
     * <code>IPropertySource</code> method
     * defines the following Setable properties
     *
     *	1) P_USERID, expects String
     *	2) P_DOMAIN, expects String
     */
    @Override
