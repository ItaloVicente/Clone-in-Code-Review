		Set<String> nameSet = properties.stringPropertyNames();
		String[] names = new String[nameSet.size()];

		Iterator<String> it = nameSet.iterator();
		for (int i = 0; it.hasNext() && i < names.length; i++) {
			names[i] = it.next();
