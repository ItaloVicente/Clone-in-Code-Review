		IObservableValue modelField1 = new WritableValue(Integer.valueOf(1),
				Integer.TYPE);
		IObservableValue modelField2 = new WritableValue(Integer.valueOf(4),
				Integer.TYPE);
		dbc.bindValue(validator.observeValidatedValue(middleField1),
				modelField1);
		dbc.bindValue(validator.observeValidatedValue(middleField2),
				modelField2);
