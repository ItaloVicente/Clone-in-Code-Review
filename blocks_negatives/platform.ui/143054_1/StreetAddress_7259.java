        if (propKey.equals(P_ID_BUILD_NO))
            return getBuildNo().toString();
        if (propKey.equals(P_ID_APTBOX))
            return getAptBox();
        if (propKey.equals(P_ID_STREET))
            return getStreetName();
        return null;
    }

    /**
     * Returns the street name
     */
    private String getStreetName() {
        if (streetName == null)
            streetName = STREETNAME_DEFAULT;
        return streetName;
    }

    @Override
