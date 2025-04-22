					mi.setText(action.getText());
					mi.setSelection(currentRadioSelection == j);
					if (style == SWT.RADIO) {
						mi.addSelectionListener(new SelectionAdapter() {

							@Override
							public void widgetSelected(SelectionEvent e) {
								if (currentRadioSelection == j) {
									items[currentRadioSelection].setSelection(true);
									return;
								}
								actions[j].run();

								items[currentRadioSelection].setSelection(false);
								currentRadioSelection = j;
								items[currentRadioSelection].setSelection(true);
								othersWorkingSetItem.setEnabled(othersWorkingSetAction.isEnabled());
