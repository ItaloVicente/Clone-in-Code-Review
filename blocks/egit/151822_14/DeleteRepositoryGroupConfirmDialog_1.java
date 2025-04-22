	@Override
	protected void okPressed() {
		shouldShowAgain = !dontShowAgain.getSelection();
		super.okPressed();
	}

	public boolean showAgain() {
		return shouldShowAgain;
	}

