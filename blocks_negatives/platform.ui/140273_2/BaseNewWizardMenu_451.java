    /**
     * TODO: should this be done with an addition listener?
     */
    private final IRegistryChangeListener registryListener = event -> {
	    if (getParent() != null) {
	        getParent().markDirty();
	    }
