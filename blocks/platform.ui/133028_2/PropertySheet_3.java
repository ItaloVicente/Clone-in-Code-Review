	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (IPropertySheetPage.class.equals(adapter)) {
			return Platform.getAdapterManager().getAdapter(this, adapter);
		}
		return super.getAdapter(adapter);
	}

