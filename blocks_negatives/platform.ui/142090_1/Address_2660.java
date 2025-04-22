        if (P_ID_POSTALCODE.equals(name)) {
            setPostalCode((String) value);
            return;
        }
        if (P_ID_CITY.equals(name)) {
            setCity((String) value);
            return;
        }
        if (P_ID_PROVINCE.equals(name)) {
            setProvince((Integer) value);
            return;
        }
    }

    /**
     * Sets the province
     */
    private void setProvince(Integer newProvince) {
        province = newProvince;
    }

    /**
     * Sets the street
     */
    private void setStreet(StreetAddress newStreet) {
        street = newStreet;
    }

    /**
     * The value as displayed in the Property Sheet.
     * @return java.lang.String
     */
    @Override
