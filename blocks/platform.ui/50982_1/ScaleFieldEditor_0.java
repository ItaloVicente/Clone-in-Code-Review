
	@Override
	public void setEnabled(boolean enabled, Composite parent) {
		super.setEnabled(enabled, parent);
		getScaleControl().setEnabled(enabled);
	}

