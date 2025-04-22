        if (name.equals(P_ID_USERID)) {
            setUserid((String) value);
            return;
        }
        if (name.equals(P_ID_DOMAIN)) {
            setDomain((String) value);
            return;
        }
    }

    /**
     * Sets the userid
     */
    private void setUserid(String newUserid) {
        userid = newUserid;
    }

    /**
     * The value as displayed in the Property Sheet.
     * @return java.lang.String
     */
    @Override
