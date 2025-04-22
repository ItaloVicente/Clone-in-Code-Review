	@Override
	protected void inputChanged(final Object input, final Object oldInput) {
		if (oldInput == null && input == null)
			return;
		super.inputChanged(input, oldInput);
		revealFirstInterestingElement();
	}

