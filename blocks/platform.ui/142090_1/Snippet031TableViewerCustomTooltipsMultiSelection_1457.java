			Label label = (Label)event.widget;
			Shell shell = label.getShell ();
			switch (event.type) {
			case SWT.MouseDown: /* Handle a user Click */
				Event e = new Event ();
				e.item = (TableItem) label.getData ("_TableItem_");
				Table table = ((TableItem) e.item).getParent();

				TableItem [] newSelection = null;
				if (isCTRLDown(event)) {
					TableItem[] sel = table.getSelection();
					for (int i = 0; i < sel.length; ++i) {
						if (e.item.equals(sel[i])) {
							newSelection = new TableItem[sel.length - 1];
							System.arraycopy(sel, 0, newSelection, 0, i);
							System.arraycopy(sel, i+1, newSelection, i, sel.length - i - 1);
							break;
						}
					}

					if (newSelection == null) {
						newSelection = new TableItem[sel.length + 1];
						System.arraycopy(sel, 0, newSelection, 0, sel.length);
						newSelection[sel.length] = (TableItem) e.item;
					}

				} else {
					newSelection = new TableItem[] { (TableItem) e.item };
				}
				table.setSelection (newSelection);
				table.notifyListeners (SWT.Selection, e);

				shell.dispose ();
				table.setFocus();
				break;
			case SWT.MouseExit:
				shell.dispose ();
				break;
			}
		}};
