		return new Predicate<SelectionListenerFactory.ISelectionModel>() {

			@Override
			public boolean test(ISelectionModel pT) {
				fCounter++;
				System.out.println("Return " + bool);
				return bool;
			}
