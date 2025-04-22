		Collection<Binding> sortedMatches = new TreeSet<Binding>((binding1, binding2) -> {
			ParameterizedCommand cmdA = binding1.getParameterizedCommand();
			ParameterizedCommand cmdB = binding2.getParameterizedCommand();
			int result = 0;
			try {
				result = cmdA.getName().compareTo(cmdB.getName());
			} catch (NotDefinedException e) {
