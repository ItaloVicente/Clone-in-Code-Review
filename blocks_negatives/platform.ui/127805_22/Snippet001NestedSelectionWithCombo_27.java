			IObservableValue selectedPerson = ViewersObservables
					.observeSingleSelection(peopleListViewer);
			Class selectedPersonValueType = null;
			if (selectedPerson.getValueType() instanceof Class<?>) {
				selectedPersonValueType = (Class) selectedPerson.getValueType();
			}
