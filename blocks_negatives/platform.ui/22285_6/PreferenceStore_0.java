		ArrayList<String> list = new ArrayList<String>();
		Enumeration<String> it = (Enumeration<String>) properties.propertyNames();
		while (it.hasMoreElements()) {
			list.add(it.nextElement());
		}
		return list.toArray(new String[list.size()]);
