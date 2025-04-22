		int nSize = categories.size();
		ThemeElementCategory[] retArray = new ThemeElementCategory[nSize];
		categories.toArray(retArray);
		Arrays.sort(retArray, ID_COMPARATOR);
		return retArray;
	}

	void setData(String name, String value) {
		if (dataMap.containsKey(name)) {
