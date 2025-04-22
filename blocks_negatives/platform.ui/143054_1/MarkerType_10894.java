    public MarkerType[] getAllSubTypes() {
        List<MarkerType> subTypes = new ArrayList<>();
        addSubTypes(subTypes, this);
        MarkerType[] subs = new MarkerType[subTypes.size()];
        subTypes.toArray(subs);
        return subs;
    }

    private void addSubTypes(List<MarkerType> list, MarkerType superType) {
