    public MarkerType[] getAllSupertypes() {
        ArrayList<MarkerType> result = new ArrayList<>();
        getAllSupertypes(result);
        return result.toArray(new MarkerType[result.size()]);
    }

    /**
     * Appends all this type's supertypes to the given list.
     */
    private void getAllSupertypes(ArrayList<MarkerType> result) {
