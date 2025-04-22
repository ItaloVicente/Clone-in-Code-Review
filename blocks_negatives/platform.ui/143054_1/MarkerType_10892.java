    public String getLabel() {
        return label;
    }

    /**
     * Returns the types which have this type as a direct supertype.
     *
     * @return the direct subtypes of this type
     */
    public MarkerType[] getSubtypes() {
        MarkerType[] types = model.getTypes();
        ArrayList<MarkerType> result = new ArrayList<>();
        for (MarkerType type : types) {
