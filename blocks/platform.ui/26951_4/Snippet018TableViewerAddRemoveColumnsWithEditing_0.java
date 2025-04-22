		List<Object> list2 = new ArrayList<Object>(Arrays.asList(v
				.getColumnProperties()));
		list2.add(columnIndex, "email");

		String[] columnProperties = new String[list2.size()];
		list2.toArray(columnProperties);
