
	@Override
	public boolean useNativeToolTip(Object object) {
		return false;
	}

	@Override
	public String getToolTipText(Object element) {
		if (!(element instanceof MarkerEntry)) {
			return super.getToolTipText(element);
		}
		MarkerEntry markerEntry = (MarkerEntry) element;
		return field.getValue(markerEntry);
	}
