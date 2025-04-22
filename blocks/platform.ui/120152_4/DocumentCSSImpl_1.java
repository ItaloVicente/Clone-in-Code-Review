
	@Override
	public void addStyleSheetChangeListener(StyleSheetChangeListener listener) {
		styleSheetChangeListeners.add(listener);
	}

	@Override
	public void removeStyleSheetChangeListener(StyleSheetChangeListener listener) {
		styleSheetChangeListeners.remove(listener);
	}
