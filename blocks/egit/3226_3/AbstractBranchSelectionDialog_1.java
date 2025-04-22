		int selectionModel = -1;
		if ((settings & ALLOW_MULTISELECTION) != 0)
			selectionModel = SWT.MULTI;
		else
			selectionModel = SWT.SINGLE;
		FilteredTree tree = new FilteredTree(parent, selectionModel | SWT.BORDER,
