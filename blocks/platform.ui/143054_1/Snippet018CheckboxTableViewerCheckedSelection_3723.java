			final IObservableList<Person> people = BeanProperties.list(ViewModel.class, "people", Person.class)
					.observe(viewModel);

			addPersonButton.addListener(SWT.Selection, event -> {
				InputDialog dlg = new InputDialog(shell, "Add Person", "Enter name:", "<Name>", newText -> {
					if (newText == null || newText.length() == 0)
						return "Name cannot be empty";
					return null;
				});
				if (dlg.open() == Window.OK) {
					Person person = new Person();
					person.setName(dlg.getValue());
					people.add(person);
					peopleViewer.setSelection(new StructuredSelection(person));
