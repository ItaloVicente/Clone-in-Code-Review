				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					final Text text = new Text(cursor, SWT.NONE);
					TableItem row = cursor.getRow();
					int rowIndex = table.indexOf(row);
					int columnIndex = cursor.getColumn();
					text.setText((String) cellFormulas[rowIndex][columnIndex].getValue());
					text.addKeyListener(new KeyAdapter() {
