		List<String> sortedByOutdegree = new ArrayList<String>(requires.keySet());
		Comparator<String> outdegreeSorter = new Comparator<String>() {
			public int compare(String o1, String o2) {
				assert requires.containsKey(o1) && requires.containsKey(o2);
				return requires.get(o1).size() - requires.get(o2).size();
			}
		};
		Collections.sort(sortedByOutdegree, outdegreeSorter);
		if (!requires.get(sortedByOutdegree.get(0)).isEmpty()) {
