	protected void updateRebaseButtonVisibility(final boolean isRebasing) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				showControl(rebaseLabel, isRebasing);
				showControl(rebaseAbortButton, isRebasing);
				showControl(rebaseContinueButton, isRebasing);
				showControl(rebaseSkipButton, isRebasing);
				commitMessageSection.layout(true);
			}

			private void showControl(Control c, final boolean show) {
				c.setVisible(show);
				GridData g = (GridData) c.getLayoutData();
				g.exclude = !show;
			}
		});
	}

