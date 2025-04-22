
		externalComposite.setVisible(!internalMode);
		parentRepoComposite.setVisible(internalMode);
		GridData gd;
		gd = (GridData) parentRepoComposite.getLayoutData();
		gd.exclude = !internalMode;

		gd = (GridData) externalComposite.getLayoutData();
		gd.exclude = internalMode;

		((Composite) getControl()).layout(true);
