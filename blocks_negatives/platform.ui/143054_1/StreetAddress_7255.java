    /**
     * Convenience Street constructor. AptBox set to default
     */
    public StreetAddress(int buildNo, String streetName) {
        super();
        setBuildNo(Integer.valueOf(buildNo));
        setStreetName(streetName);
    }
