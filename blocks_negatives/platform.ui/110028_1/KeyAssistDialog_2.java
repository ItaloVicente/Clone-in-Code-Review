		matches = new TreeSet<Binding>(new Comparator<Binding>() {
			@Override
			public int compare(Binding a, Binding b) {
				Binding bindingA = a;
				Binding bindingB = b;
				ParameterizedCommand commandA = bindingA.getParameterizedCommand();
				ParameterizedCommand commandB = bindingB.getParameterizedCommand();
				try {
					return commandA.getName().compareTo(commandB.getName());
				} catch (NotDefinedException e) {
					return 0;
				}
