	@Override
	protected void inputChanged(Object input, Object oldInput) {
		if (input == null)
			sash.setMaximizedControl(errorText.getControl());
		else
			sash.setMaximizedControl(getTable());
		super.inputChanged(input, oldInput);
	}

