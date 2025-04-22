		return null;
	}

	public void fireSelection() {
		selectionProvider.fireSelection();
	}

	protected void firePropertyChange(int propertyId) {
		Object[] listeners = getListeners();
		for (Object listener : listeners) {
			IPropertyListener l = (IPropertyListener) listener;
			l.propertyChanged(this, propertyId);
		}
	}

	private boolean siteState = false;

	protected void setSiteInitialized(boolean initialized) {
		siteState = initialized;
	}

	public boolean isSiteInitialized() {
		return siteState;
	}
