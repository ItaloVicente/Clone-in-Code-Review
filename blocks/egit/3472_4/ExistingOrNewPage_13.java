
		externalComposite.setVisible(externalMode);
		parentRepoComposite.setVisible(!externalMode);
		GridData gd;
		gd = (GridData) parentRepoComposite.getLayoutData();
		gd.exclude = externalMode;

		gd = (GridData) externalComposite.getLayoutData();
		gd.exclude = !externalMode;

		((Composite) getControl()).layout(true);
