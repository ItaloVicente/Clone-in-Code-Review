		IObservableMap firstName = BeanProperties.value(Contact.class, "firstName")
				.observeDetail(contentProvider.getKnownElements());
		IObservableMap lastName = BeanProperties.value(Contact.class, "lastName")
				.observeDetail(contentProvider.getKnownElements());
		IObservableMap[] attributes = { firstName, lastName };

