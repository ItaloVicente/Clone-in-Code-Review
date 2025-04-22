        if (property.equals(P_ID_BUILD_NO)) {
            setBuildNo(BUILD_NO_DEFAULT);
            return;
        }
        if (property.equals(P_ID_APTBOX)) {
            setAptBox(APTBOX_DEFAULT);
            return;
        }
        if (property.equals(P_ID_STREET)) {
            setStreetName(STREETNAME_DEFAULT);
            return;
        }
    }

    /**
     * Sets the appartment number
     */
    private void setAptBox(String newAptBox) {
        aptBox = newAptBox;
    }

    /**
     * Sets the building number
     */
    private void setBuildNo(Integer newBuildNo) {
        buildNo = newBuildNo;
    }

    /**
     * The <code>Name</code> implementation of this
     * <code>IPropertySource</code> method
     * defines the following Setable properties
     *
     * 	1) P_BUILD_NO expects java.lang.Integer
     * 	2) P_APTBOX expects java.lang.String
     *	3) P_STREET expects java.lang.String
     */
    @Override
