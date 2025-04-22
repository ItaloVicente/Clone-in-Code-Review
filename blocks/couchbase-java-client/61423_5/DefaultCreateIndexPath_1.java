
    @Override
    public OnPrimaryPath createPrimary(String customPrimaryName) {
        element(new IndexElement(customPrimaryName, true));
        return new DefaultOnPrimaryPath(this);
    }
