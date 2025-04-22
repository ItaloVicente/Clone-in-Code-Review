					mi.setText(actions[j].getText());
					mi.setSelection(currentSelection == j);
					mi.addSelectionListener(new SelectionAdapter() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							if (currentSelection == j) {
								items[currentSelection].setSelection(true);
								return;
