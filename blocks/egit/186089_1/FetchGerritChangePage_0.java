			placeholder = new Label(main, SWT.NONE);
			GridDataFactory.fillDefaults().span(2, 1).applyTo(placeholder);
		} else if ("cherry-pick".equals(defaultCommand)) { //$NON-NLS-1$
			cherryPickFetchHead.setSelection(true);
			placeholder = new Label(main, SWT.NONE);
			GridDataFactory.fillDefaults().span(2, 1).applyTo(placeholder);
