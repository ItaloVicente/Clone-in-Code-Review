		createSeperator(parent);
		super.createAttributesArea(parent);

		Label label = new Label(parent, SWT.NONE);
		label.setText(MarkerMessages.propertiesDialog_priority);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);

		priorityCombo = new Combo(composite, SWT.READ_ONLY);
		priorityCombo.setItems(new String[] { PRIORITY_HIGH, PRIORITY_NORMAL,
				PRIORITY_LOW });
		priorityCombo.addTraverseListener(e -> {
			if (e.detail == SWT.TRAVERSE_ESCAPE
					|| e.detail == SWT.TRAVERSE_RETURN) {
				e.doit = false;
			}
