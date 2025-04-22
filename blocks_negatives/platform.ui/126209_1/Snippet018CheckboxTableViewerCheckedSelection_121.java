			addPersonButton.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(Event event) {
					InputDialog dlg = new InputDialog(shell, "Add Person",
							"Enter name:", "<Name>", new IInputValidator() {
								@Override
								public String isValid(String newText) {
									if (newText == null
											|| newText.length() == 0)
										return "Name cannot be empty";
									return null;
								}
							});
					if (dlg.open() == Window.OK) {
						Person person = new Person();
						person.setName(dlg.getValue());
						people.add(person);
						peopleViewer.setSelection(new StructuredSelection(
								person));
					}
