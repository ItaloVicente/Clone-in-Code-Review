		IObservableFactory<Object, ? extends IObservableSet<Object>> childrenFactory = element -> {
			if (element == tree.getInput()) {
				WritableSet<Object> topElements = new WritableSet<>();
				topElements.add(new SimpleNode("Random Set 1", set1));
				topElements.add(new SimpleNode("Random Set 2", set2));
				topElements.add(new SimpleNode("Random Set 3", set3));
				topElements.add(new SimpleNode("Union of the other sets", union));
				return topElements;
