    /**
     * The <code>AbstractGroupMarker</code> implementation of this
     * <code>IContributionItem</code> method returns <code>true</code> iff the
     * id is not <code>null</code>. Subclasses may override.
     */
    @Override
    public boolean isGroupMarker() {
        return getId() != null;
    }
