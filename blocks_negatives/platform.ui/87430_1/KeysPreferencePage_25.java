		Comparator comparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String name1 = null;
				String name2 = null;
				try {
					name1 = ((ParameterizedCommand) o1).getName();
				} catch (NotDefinedException e) {
					return -1;
				}
				try {
					name2 = ((ParameterizedCommand) o2).getName();
				} catch (NotDefinedException e) {
					return 1;
				}
				int rc = collator.compare(name1, name2);
				if (rc != 0) {
					return rc;
				}

				String id1 = ((ParameterizedCommand) o1).getId();
				String id2 = ((ParameterizedCommand) o2).getId();
				return collator.compare(id1, id2);
