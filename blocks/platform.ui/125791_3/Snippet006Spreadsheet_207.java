		Realm.runWithDefault(DisplayRealm.getRealm(display), new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell(display);
				shell.setText("Data Binding Snippet 006");

				final Table table = new Table(shell, SWT.BORDER | SWT.MULTI
						| SWT.FULL_SELECTION | SWT.VIRTUAL);
				table.setLinesVisible(true);
				table.setHeaderVisible(true);

				for (int i = 0; i < NUM_COLUMNS; i++) {
					TableColumn tableColumn = new TableColumn(table, SWT.NONE);
					tableColumn.setText(Character.toString((char) ('A' + i)));
					tableColumn.setWidth(60);
