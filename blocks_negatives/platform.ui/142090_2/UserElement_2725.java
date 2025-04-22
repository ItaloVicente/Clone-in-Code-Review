        if (property.equals(P_ID_ADDRESS))
            return getAddress() != address_Default;
        if (property.equals(P_ID_FULLNAME))
            return getFullName() != fullName_Default;
        if (property.equals(P_ID_PHONENUMBER))
            return getPhoneNumber() != phoneNumber_Default;
        if (property.equals(P_ID_EMAIL))
            return getEmailAddress() != emailAddress_Default;
        if (property.equals(P_ID_COOP))
            return getCoop() != coop_Default;
        if (property.equals(P_ID_BDAY))
            return getBirthday() != birthday_Default;
        if (property.equals(P_ID_SALARY))
            return getSalary() != salary_Default;
        if (property.equals(P_ID_HAIRCOLOR))
            return getHairColor() != hairColor_Default;
        if (property.equals(P_ID_EYECOLOR))
            return getEyeColor() != eyeColor_Default;
        return false;
    }

    @Override
