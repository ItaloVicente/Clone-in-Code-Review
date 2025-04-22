        if (P_ID_FIRSTNAME.equals(propName)) {
            setFirstName((String) val);
            return;
        }
        if (P_ID_LASTNAME.equals(propName)) {
            setLastName((String) val);
            return;
        }
        if (P_ID_MIDDLENAME.equals(propName)) {
            setInitial((String) val);
            return;
        }
    }

    /**
     * The value as displayed in the Property Sheet. Will not print default values
     * @return java.lang.String
     */
    @Override
