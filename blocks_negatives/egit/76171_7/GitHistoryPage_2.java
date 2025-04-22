		if (showFindToolbar)
			((GridData) findToolbar.getLayoutData()).heightHint = SWT.DEFAULT;
		else {
			((GridData) findToolbar.getLayoutData()).heightHint = 0;
			findToolbar.clear();
		}
