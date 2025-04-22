		assertEquals(set("a", "D/b", "D/D/d"), stat.getModified());
	}

	public static Set<String> set(String... elements) {
		Set<String> ret = new HashSet<String>();
		for (String element : elements)
			ret.add(element);
		return ret;
