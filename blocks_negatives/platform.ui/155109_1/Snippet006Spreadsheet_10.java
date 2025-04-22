
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					final Text text = new Text(cursor, SWT.NONE);
					TableItem row = cursor.getRow();
					int rowIndex = table.indexOf(row);
					int columnIndex = cursor.getColumn();
					text.setText(cellFormulas[rowIndex][columnIndex].getValue());
					text.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {
							if (e.character == SWT.CR) {
								TableItem row = cursor.getRow();
								int rowIndex = table.indexOf(row);
								int columnIndex = cursor.getColumn();
								cellFormulas[rowIndex][columnIndex].setValue(text.getText());
								text.dispose();
							}
							if (e.character == SWT.ESC) {
								text.dispose();
							}
						}
					});
					editor.setEditor(text);
					text.setFocus();
