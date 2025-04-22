        return toString().equals(ob.toString());
    }

    /**
     * the appartment number
     */
    private String getAptBox() {
        if (aptBox == null)
            aptBox = APTBOX_DEFAULT;
        return aptBox;
    }

    /**
     * Returns the building number
     */
    private Integer getBuildNo() {
        if (buildNo == null)
            buildNo = BUILD_NO_DEFAULT;
        return buildNo;
    }

    /**
     * Returns the descriptors
     */
    private static ArrayList<TextPropertyDescriptor> getDescriptors() {
        return descriptors;
    }

    @Override
