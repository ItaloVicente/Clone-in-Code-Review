	protected void updateRebaseButtonVisibility(final boolean isRebasing) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				rebaseLabel.setVisible(isRebasing);
				rebaseAbortButton.setVisible(isRebasing);
				rebaseContinueButton.setVisible(isRebasing);
				rebaseSkipButton.setVisible(isRebasing);
			}
		});
	}

