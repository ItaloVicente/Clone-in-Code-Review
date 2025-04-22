
				final TableCursor cursor = new TableCursor(table, SWT.NONE);
				final ControlEditor editor = new ControlEditor(cursor);
				editor.grabHorizontal = true;
				editor.grabVertical = true;

				cursor.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						table.setSelection(new TableItem[] { cursor.getRow() });
