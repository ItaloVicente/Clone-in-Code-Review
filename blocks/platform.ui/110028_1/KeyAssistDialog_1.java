		matches = new TreeSet<Binding>((a, b) -> {
			Binding bindingA = a;
			Binding bindingB = b;
			ParameterizedCommand commandA = bindingA.getParameterizedCommand();
			ParameterizedCommand commandB = bindingB.getParameterizedCommand();
			try {
				return commandA.getName().compareTo(commandB.getName());
			} catch (NotDefinedException e) {
				return 0;
