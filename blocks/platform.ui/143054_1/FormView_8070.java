		Button toggleTitleSelectable = toolkit.createButton(form.getBody(), "Toggle Title Text Selectable", SWT.NONE);
		toggleTitleSelectable.setText("Toggle Title Text Selectable");
		toggleTitleSelectable.addSelectionListener(SelectionListener.widgetSelectedAdapter(
				e -> form.getForm().setTitleTextSelectable(titleTextSelectable = !titleTextSelectable)));
		td = new TableWrapData(TableWrapData.FILL_GRAB);
		td.colspan = 2;
		toggleTitleSelectable.setLayoutData(td);
