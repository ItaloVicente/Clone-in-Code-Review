		Comparator comparator = (o1, o2) -> {
			String name1 = null;
			String name2 = null;
			try {
				name1 = ((ParameterizedCommand) o1).getName();
			} catch (NotDefinedException e1) {
				return -1;
