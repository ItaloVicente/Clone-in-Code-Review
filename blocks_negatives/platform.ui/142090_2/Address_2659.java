        if (P_ID_POSTALCODE.equals(property)) {
            setPostalCode(POSTALCODE_DEFAULT);
            return;
        }
        if (P_ID_CITY.equals(property)) {
            setCity(CITY_DEFAULT);
            return;
        }
        if (P_ID_PROVINCE.equals(property)) {
            setProvince(PROVINCE_DEFAULT);
            return;
        }
        if (P_ID_STREET.equals(property)) {
            setStreet(new StreetAddress());
            return;
        }
    }

    /**
     * Sets the city
     */
    private void setCity(String newCity) {
        city = newCity;
    }

    /**
     * Sets the postal code
     */
    private void setPostalCode(String newPostalCode) {
        this.postalCode = newPostalCode.toUpperCase();
    }

    /**
     * The <code>Address</code> implementation of this
     * <code>IPropertySource</code> method
     * defines the following Setable properties
     *
     * 	1) P_CITY expects java.lang.String
     * 	2) P_POSTALCODE expects java.lang.String
     *  3) P_PROVINCE expects java.lang.String
     *
     * <p>P_ID_STREET is not set here since it is referenced
     * and set directly in StreetAddress.
     * According to IPropertySource, StreetAddress.getEditableValue
     * should return a String which will be passed to this method
     * as the value. A new StreetAddress object should then be
     * created from the string.
     * An alternative would be to return the StreetAddress
     * directly in StreetAddress.getEditableValue and define a
     * cell editor for the StreetAddress property.
     * This was ommitted for the sake of simplicity.
     */
    @Override
