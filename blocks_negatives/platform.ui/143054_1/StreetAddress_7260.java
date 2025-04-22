        if (property.equals(P_ID_BUILD_NO))
            return getBuildNo() != BUILD_NO_DEFAULT;
        if (property.equals(P_ID_APTBOX))
            return getAptBox() != APTBOX_DEFAULT;
        if (property.equals(P_ID_STREET))
            return getStreetName() != STREETNAME_DEFAULT;
        return false;
    }

    @Override
