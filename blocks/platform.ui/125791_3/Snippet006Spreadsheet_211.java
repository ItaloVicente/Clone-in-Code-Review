					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						final Text text = new Text(cursor, SWT.NONE);
						TableItem row = cursor.getRow();
						int rowIndex = table.indexOf(row);
						int columnIndex = cursor.getColumn();
						text
								.setText((String) cellFormulas[rowIndex][columnIndex]
										.getValue());
						text.addKeyListener(new KeyAdapter() {
							@Override
							public void keyPressed(KeyEvent e) {
								if (e.character == SWT.CR) {
									TableItem row = cursor.getRow();
									int rowIndex = table.indexOf(row);
									int columnIndex = cursor.getColumn();
									cellFormulas[rowIndex][columnIndex]
											.setValue(text.getText());
									text.dispose();
								}
								if (e.character == SWT.ESC) {
									text.dispose();
								}
							}
						});
						editor.setEditor(text);
						text.setFocus();
					}
				});
				cursor.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.keyCode == SWT.MOD1 || e.keyCode == SWT.MOD2
								|| (e.stateMask & SWT.MOD1) != 0
								|| (e.stateMask & SWT.MOD2) != 0) {
							cursor.setVisible(false);
						}
					}
				});
				table.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						if (e.keyCode == SWT.MOD1
								&& (e.stateMask & SWT.MOD2) != 0)
							return;
						if (e.keyCode == SWT.MOD2
								&& (e.stateMask & SWT.MOD1) != 0)
							return;
						if (e.keyCode != SWT.MOD1
								&& (e.stateMask & SWT.MOD1) != 0)
							return;
						if (e.keyCode != SWT.MOD2
								&& (e.stateMask & SWT.MOD2) != 0)
							return;

						TableItem[] selection = table.getSelection();
						TableItem row = (selection.length == 0) ? table
								.getItem(table.getTopIndex()) : selection[0];
						table.showItem(row);
						cursor.setSelection(row, 0);
						cursor.setVisible(true);
						cursor.setFocus();
					}
				});

				GridLayoutFactory.fillDefaults().generateLayout(shell);
				shell.setSize(400, 300);
				shell.open();
