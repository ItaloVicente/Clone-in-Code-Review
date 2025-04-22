		WritableList masterObservableList = new WritableList();
		masterObservableList.add(new SimplePerson());
		masterObservableList.add(new SimplePerson());
		ListDetailValueObservableList ldol = new ListDetailValueObservableList(
				masterObservableList, BeansObservables.valueFactory("name"),
				null);
