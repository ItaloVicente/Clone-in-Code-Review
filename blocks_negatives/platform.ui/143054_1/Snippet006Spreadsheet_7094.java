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
