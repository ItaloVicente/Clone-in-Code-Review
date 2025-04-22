		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = new Shell(display);
			shell.setText("Data Binding Snippet 006");

			final Table table = new Table(shell, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION | SWT.VIRTUAL);
			table.setLinesVisible(true);
			table.setHeaderVisible(true);

			for (int i1 = 0; i1 < NUM_COLUMNS; i1++) {
				TableColumn tableColumn = new TableColumn(table, SWT.NONE);
				tableColumn.setText(Character.toString((char) ('A' + i1)));
				tableColumn.setWidth(60);
			}
			WritableList list = new WritableList();
			for (int i2 = 0; i2 < NUM_ROWS; i2++) {
				list.add(new Object());
				for (int j = 0; j < NUM_COLUMNS; j++) {
					cellFormulas[i2][j] = new WritableValue();
					cellValues[i2][j] = new ComputedCellValue(cellFormulas[i2][j]);
					if (!FUNKY_FORMULAS || i2 == 0 || j == 0) {
						cellFormulas[i2][j].setValue("");
					} else {
						cellFormulas[i2][j].setValue("=" + cellReference(i2 - 1, j) + "+" + cellReference(i2, j - 1));
					}
