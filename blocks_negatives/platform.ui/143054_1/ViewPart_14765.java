    /**
     * Listens to PROP_TITLE property changes in this object until the first call to
     * setContentDescription. Used for compatibility with old parts that call setTitle
     * or overload getTitle instead of using setContentDescription.
     */
    private IPropertyListener compatibilityTitleListener = (source, propId) -> {
	    if (propId == IWorkbenchPartConstants.PROP_TITLE) {
	        setDefaultContentDescription();
	    }
