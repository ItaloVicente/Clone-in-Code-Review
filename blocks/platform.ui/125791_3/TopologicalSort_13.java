		Comparator<ID> outdegreeSorter = new Comparator<ID>() {
			@Override
			public int compare(ID o1, ID o2) {
				assert requires.containsKey(o1) && requires.containsKey(o2);
				int comparison = requires.get(o1).size() - requires.get(o2).size();
				if (comparison == 0) {
					return depends.get(o2).size() - depends.get(o1).size();
				}
				return comparison;
