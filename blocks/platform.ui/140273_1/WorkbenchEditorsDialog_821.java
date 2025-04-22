				TableColumn c[] = editorsTable.getColumns();
				if (columnsWidth == null) {
					int w = editorsTable.getClientArea().width;
					c[0].setWidth(w * 1 / 3);
					c[1].setWidth(w - c[0].getWidth());
				} else {
					c[0].setWidth(columnsWidth[0]);
					c[1].setWidth(columnsWidth[1]);
				}
				editorsTable.setLayout(null);
			}
		});
		TableColumn tc = new TableColumn(editorsTable, SWT.NONE);
		tc.setResizable(true);
		tc.setText(WorkbenchMessages.WorkbenchEditorsDialog_name);
		tc.addSelectionListener(headerListener);
		tc = new TableColumn(editorsTable, SWT.NONE);
		tc.setResizable(true);
		tc.setText(WorkbenchMessages.WorkbenchEditorsDialog_path);
		tc.addSelectionListener(headerListener);

		Composite selectionButtons = new Composite(dialogArea, SWT.NULL);
		Label compLabel = new Label(selectionButtons, SWT.NULL);
		compLabel.setFont(font);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		selectionButtons.setLayout(layout);

		selectClean = new Button(selectionButtons, SWT.PUSH);
		selectClean.setText(WorkbenchMessages.WorkbenchEditorsDialog_selectClean);
		selectClean.addSelectionListener(widgetSelectedAdapter(e -> {
			editorsTable.setSelection(selectClean(editorsTable.getItems()));
			updateButtons();
