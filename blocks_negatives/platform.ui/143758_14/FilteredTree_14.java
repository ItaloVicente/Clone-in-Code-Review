			if (!useNewLook || useNativeSearchField(parent)) {
				filterComposite = new Composite(this, SWT.NONE);
			} else {
				filterComposite = new Composite(this, SWT.BORDER);
				filterComposite.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
			}
			GridLayout filterLayout = new GridLayout(2, false);
