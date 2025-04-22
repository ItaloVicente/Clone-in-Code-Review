	@Override
	protected void okPressed() {
		toggleState = toggle.getSelection();
		super.okPressed();
	}

	public boolean showAgain() {
		return !toggleState;
	}

