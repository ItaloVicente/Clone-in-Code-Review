		final TableColumn tableColumnCategory = new TableColumn(tableBindings, SWT.NONE, VIEW_CATEGORY_COLUMN_INDEX);
		tableColumnCategory.setText(SORTED_COLUMN_NAMES[VIEW_CATEGORY_COLUMN_INDEX]);
		tableColumnCategory.addSelectionListener(new SortOrderSelectionListener(VIEW_CATEGORY_COLUMN_INDEX));
		final TableColumn tableColumnCommand = new TableColumn(tableBindings, SWT.NONE, VIEW_COMMAND_COLUMN_INDEX);
		tableColumnCommand.setText(UNSORTED_COLUMN_NAMES[VIEW_COMMAND_COLUMN_INDEX]);
		tableColumnCommand.addSelectionListener(new SortOrderSelectionListener(VIEW_COMMAND_COLUMN_INDEX));
		final TableColumn tableColumnKeySequence = new TableColumn(tableBindings, SWT.NONE,
				VIEW_KEY_SEQUENCE_COLUMN_INDEX);
		tableColumnKeySequence.setText(UNSORTED_COLUMN_NAMES[VIEW_KEY_SEQUENCE_COLUMN_INDEX]);
		tableColumnKeySequence.addSelectionListener(new SortOrderSelectionListener(VIEW_KEY_SEQUENCE_COLUMN_INDEX));
		final TableColumn tableColumnContext = new TableColumn(tableBindings, SWT.NONE, VIEW_CONTEXT_COLUMN_INDEX);
		tableColumnContext.setText(UNSORTED_COLUMN_NAMES[VIEW_CONTEXT_COLUMN_INDEX]);
		tableColumnContext.addSelectionListener(new SortOrderSelectionListener(VIEW_CONTEXT_COLUMN_INDEX));
