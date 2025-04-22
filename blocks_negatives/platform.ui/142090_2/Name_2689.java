        if (P_ID_FIRSTNAME.equals(property)) {
            setFirstName(FIRSTNAME_DEFAULT);
            return;
        }
        if (P_ID_LASTNAME.equals(property)) {
            setLastName(LASTNAME_DEFAULT);
            return;
        }
        if (P_ID_MIDDLENAME.equals(property)) {
            setInitial(MIDDLENAME_DEFAULT);
            return;
        }
    }

    /**
     * Sets the first name
     */
    private void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    /**
     * Sets the initial
     */
    private void setInitial(String newInitial) {
        initial = newInitial;
    }

    /**
     * Sets the last name
     */
    private void setLastName(String newLastName) {
        lastName = newLastName;
    }

    /**
     * The <code>Name</code> implementation of this
     * <code>IPropertySource</code> method
     * defines the following Setable properties
     *
     *	1) P_FIRST, expects String, sets the firstname of this OrganizationElement
     *  2) P_MIDDLENAME, expects String, sets middlename of this OrganizationElement
     *  3) P_LASTNAME, expects String, sets lastname of this OrganizationElement
     */
    @Override
