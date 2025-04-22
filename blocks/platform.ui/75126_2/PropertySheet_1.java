		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
	}

	@Override
	public <T> T getAdapter(Class<T> key) {
		if (ISaveablePart.class.equals(key)) {
			return key.cast(getSaveablePart());
		}
		return super.getAdapter(key);
