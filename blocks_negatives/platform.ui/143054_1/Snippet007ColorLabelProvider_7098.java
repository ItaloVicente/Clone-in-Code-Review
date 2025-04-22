				viewer.setLabelProvider(new ColorLabelProvider(attributes));

				viewer.setInput(observableList);

				table.getColumn(0).pack();

				Button button = new Button(shell, SWT.PUSH);
				button.setText("Toggle Gender");
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent arg0) {
						IStructuredSelection selection = viewer.getStructuredSelection();
						if (selection != null && !selection.isEmpty()) {
							Person person = (Person) selection
									.getFirstElement();
							person
									.setGender((person.getGender() == Person.MALE) ? Person.FEMALE
											: Person.MALE);
						}
					}
				});
