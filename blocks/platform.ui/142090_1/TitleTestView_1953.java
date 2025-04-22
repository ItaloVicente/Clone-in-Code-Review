		composite = new Composite(parent, SWT.NONE);
		CellLayout layout = new CellLayout(2).setColumn(0, Row.fixed())
				.setColumn(1, Row.growing());
		composite.setLayout(layout);

		Label firstLabel = new Label(composite, SWT.NONE);
		firstLabel.setText("Title");
		title = new Text(composite, SWT.BORDER);
		title.setText(getTitle());

		title.addModifyListener(new ModifyListener() {
			@Override
