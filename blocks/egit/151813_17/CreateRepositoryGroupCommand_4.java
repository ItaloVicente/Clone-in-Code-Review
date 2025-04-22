		String name = groups.getGroups().get(0).getName() + '_';
		for (int i = 0; i < 100; i++) {
			if (!groups.groupExists(name)) {
				return name;
			}
			name += '_';
		}
		return null;
