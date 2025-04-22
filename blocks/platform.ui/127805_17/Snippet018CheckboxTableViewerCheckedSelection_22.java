			IObservableValue<Boolean> personSelected =  ComputedValue.create(() -> selectedPerson.getValue() != null);
			dbc.bindValue(WidgetProperties.enabled().observe(removePersonButton), personSelected);
			dbc.bindValue(WidgetProperties.enabled().observe(friendsViewer.getTable()), personSelected);

			dbc.bindValue(WidgetProperties.text(SWT.Modify).observe(personName),
					BeanProperties.value(Person.class, "name", String.class).observeDetail(selectedPerson));

			ViewerSupport.bind(friendsViewer, people, BeanProperties.value(Person.class, "name"));

			dbc.bindSet(ViewersObservables.observeCheckedElements(friendsViewer, Person.class),
					BeanProperties.set(Person.class, "friends", Person.class).observeDetail(selectedPerson));
