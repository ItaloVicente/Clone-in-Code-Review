        return null;
    }

    /**
     * Fires a selection out.
     */
    public void fireSelection() {
        selectionProvider.fireSelection();
    }

    /**
     * Fires a property change event.
     */
    protected void firePropertyChange(int propertyId) {
        Object[] listeners = getListeners();
        for (Object listener : listeners) {
            IPropertyListener l = (IPropertyListener) listener;
            l.propertyChanged(this, propertyId);
        }
    }

    /**
     * boolean to declare whether the site was properly initialized in the init method.
     */
    private boolean siteState = false;

    /**
     * Sets whether the site was properly initialized in the init method.
     */
    protected void setSiteInitialized(boolean initialized) {
        siteState = initialized;
    }

    /**
     * Gets whether the site was properly initialized in the init method.
     */
    public boolean isSiteInitialized() {
        return siteState;
    }
