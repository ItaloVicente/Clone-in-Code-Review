	protected void updateRebaseButtonVisibility(final boolean isRebasing) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				showControl(rebaseSection, isRebasing);
				rebaseSection.getParent().layout(true);
			}

			private void showControl(Control c, final boolean show) {
				c.setVisible(show);
				GridData g = (GridData) c.getLayoutData();
				g.exclude = !show;
			}
		});
	}

