	private static IObservableList<IObservable> getObservableList(IObservable[] observables) {
		IObservableList<IObservable> observableList = new WritableList<>();
		for (IObservable observable : observables) {
			observableList.add(observable);
		}

		return observableList;
	}

