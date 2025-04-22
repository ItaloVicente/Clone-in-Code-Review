		Collection<Binding> sortedMatches = new TreeSet<Binding>(new Comparator<Binding>() {
			@Override
			public int compare(Binding binding1, Binding binding2) {
				ParameterizedCommand cmdA = binding1.getParameterizedCommand();
				ParameterizedCommand cmdB = binding2.getParameterizedCommand();
				int result = 0;
				try {
					result = cmdA.getName().compareTo(cmdB.getName());
				} catch (NotDefinedException e) {
				}
				return result;
