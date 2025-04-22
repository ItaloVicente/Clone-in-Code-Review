	@Override
	protected Control createButtonBar(Composite parent) {
		Control result = super.createButtonBar(parent);
		setOkButtonEnablement();
		return result;
	}

