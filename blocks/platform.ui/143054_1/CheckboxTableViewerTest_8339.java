			for (int i = 0; i < headers.length; i++) {
				layout.addColumnData(layouts[i]);
				TableColumn tc = new TableColumn(table, SWT.NONE, i);
				tc.setResizable(layouts[i].resizable);
				tc.setText(headers[i]);
				columns[i] = tc;
			}
