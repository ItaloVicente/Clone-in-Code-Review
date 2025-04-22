				if (e.keyCode == SWT.ARROW_UP && (e.stateMask & SWT.SHIFT) != 0
						&& (e.stateMask & SWT.CTRL) != 0) {
					StructuredSelection selection = (StructuredSelection) list
							.getSelection();

					if (selection.size() == 1) {
						Object element = selection.getFirstElement();
						if (element.equals(list.getElementAt(0))) {
							pattern.setFocus();
						}
						if (list.getElementAt(list.getTable()
								.getSelectionIndex() - 1) instanceof ItemsListSeparator)
							list.getTable().setSelection(
									list.getTable().getSelectionIndex() - 1);
						list.getTable().notifyListeners(SWT.Selection,
								new Event());

					}
				}

				if (e.keyCode == SWT.ARROW_DOWN
						&& (e.stateMask & SWT.SHIFT) != 0
						&& (e.stateMask & SWT.CTRL) != 0) {

					if (list
							.getElementAt(list.getTable().getSelectionIndex() + 1) instanceof ItemsListSeparator)
						list.getTable().setSelection(
								list.getTable().getSelectionIndex() + 1);
					list.getTable().notifyListeners(SWT.Selection, new Event());
