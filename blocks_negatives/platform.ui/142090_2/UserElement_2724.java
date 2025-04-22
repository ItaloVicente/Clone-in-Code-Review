        if (propKey.equals(P_ID_ADDRESS))
            return getAddress();
        if (propKey.equals(P_ID_FULLNAME))
            return getFullName();
        if (propKey.equals(P_ID_PHONENUMBER))
            return getPhoneNumber();
        if (propKey.equals(P_ID_EMAIL))
            return getEmailAddress();
        if (propKey.equals(P_ID_COOP))
            return getCoop().equals(Boolean.TRUE) ? P_VALUE_TRUE
                    : P_VALUE_FALSE;
        if (propKey.equals(P_ID_BDAY))
            return getBirthday();
        if (propKey.equals(P_ID_SALARY))
            return getSalary().toString();
        if (propKey.equals(P_ID_HAIRCOLOR))
            return getHairColor();
        if (propKey.equals(P_ID_EYECOLOR))
            return getEyeColor();
        return super.getPropertyValue(propKey);
    }

    /**
     * Returns the salary
     */
    private Float getSalary() {
        if (salary == null)
            salary = Float.valueOf(0);
        return salary;
    }

    @Override
