        if (property.equals(P_ID_PROVINCE))
            return getProvince() != PROVINCE_DEFAULT;
        if (property.equals(P_ID_STREET))
            return !STREET_DEFAULT.equals(getStreet());
        if (property.equals(P_ID_CITY))
            return getCity() != CITY_DEFAULT;
        if (property.equals(P_ID_POSTALCODE))
            return getPostalCode() != POSTALCODE_DEFAULT;
        return false;
    }

    /* (non-Javadoc)
     * Method declared on IPropertySource
     */
    @Override
