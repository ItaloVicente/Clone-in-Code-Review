				if (e.button == 3) {
					if (table.equals(e.getSource())) {
						TableItem ti = table.getItem(new Point(e.x, e.y));
						if (ti != null && ti.getData() instanceof EditorReference) {
							Menu menu = new Menu(table);
							MenuItem mi = new MenuItem(menu, SWT.NONE);
							mi.setText(WorkbenchMessages.FilteredTableBaseHandler_Close);
							mi.addListener(SWT.Selection, se -> close(ti));
							menu.setVisible(true);
						}
					}
				} else {
					ok(dialog, table);
				}
