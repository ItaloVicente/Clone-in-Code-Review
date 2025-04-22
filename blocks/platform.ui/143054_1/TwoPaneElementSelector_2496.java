		Composite contents = (Composite) super.createDialogArea(parent);
		createMessageArea(contents);
		createFilterText(contents);
		createLabel(contents, fUpperListLabel);
		createFilteredList(contents);
		createLabel(contents, fLowerListLabel);
		createLowerList(contents);
		setListElements(fElements);
		List initialSelections = getInitialElementSelections();
		if (!initialSelections.isEmpty()) {
			Object element = initialSelections.get(0);
			setSelection(new Object[] { element });
			setLowerSelectedElement(element);
		}
		return contents;
	}

	protected Label createLabel(Composite parent, String name) {
		if (name == null) {
