        if (propId == IWorkbenchPartConstants.PROP_DIRTY) {
        	IWorkbenchPart actualPart = getPart(false);
        	if (actualPart != null) {
				SaveablesList modelManager = (SaveablesList) actualPart.getSite().getService(ISaveablesLifecycleListener.class);
	        	modelManager.dirtyChanged(actualPart);
        	}
        }
    }

    protected void partPropertyChanged(PropertyChangeEvent event) {
    	firePartPropertyChange(event);
    }

    /**
     * Releases any references maintained by this part reference
     * when its actual part becomes known (not called when it is disposed).
     */
    protected void releaseReferences() {

    }

    /* package */ void addInternalPropertyListener(IPropertyListener listener) {
        internalPropChangeListeners.add(listener);
    }

    /* package */ void removeInternalPropertyListener(IPropertyListener listener) {
        internalPropChangeListeners.remove(listener);
    }

    protected void fireInternalPropertyChange(int id) {
