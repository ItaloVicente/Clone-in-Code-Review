		if (useNativeSearchField(area)) {
			filterTextArea = new Composite(area, SWT.NONE);
			filterText = new Text(filterTextArea, SWT.BORDER | SWT.SINGLE | SWT.SEARCH | SWT.ICON_CANCEL);
		} else {
			filterTextArea = new Composite(area, SWT.BORDER);
			filterText = new Text(filterTextArea, SWT.SINGLE);
		}
