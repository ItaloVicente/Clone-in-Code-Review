				String s1 = (String) o1;
				String s2 = (String) o2;
				return s1.compareTo(s2);
			}
		});
		set.addAll(properties.keySet());
		Iterator<Object> i = set.iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			String value = properties.getProperty(key);

			writer.print(key);
			writer.print('=');

			if (key.startsWith(ECLIPSE_PROPERTY_PREFIX)) {
