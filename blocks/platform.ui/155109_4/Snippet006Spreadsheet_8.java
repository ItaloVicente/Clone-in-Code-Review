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
