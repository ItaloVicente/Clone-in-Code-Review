			IObservableValue personSelected = new ComputedValue(Boolean.TYPE) {
				@Override
				protected Object calculate() {
					return Boolean.valueOf(selectedPerson.getValue() != null);
				}
			};
			dbc.bindValue(WidgetProperties.enabled().observe(removePersonButton),
					personSelected);
			dbc.bindValue(WidgetProperties.enabled().observe(friendsViewer
					.getTable()), personSelected);

			dbc.bindValue(
					WidgetProperties.text(SWT.Modify).observe(personName),
					BeanProperties.value((Class) selectedPerson.getValueType(), "name", String.class)
					.observeDetail(selectedPerson));

			ViewerSupport.bind(friendsViewer, people, BeanProperties.value(
					Person.class, "name"));

			dbc.bindSet(ViewersObservables.observeCheckedElements(
					friendsViewer, Person.class),BeanProperties.set((Class) selectedPerson.getValueType(), "friends", Person.class)
					.observeDetail(selectedPerson));
