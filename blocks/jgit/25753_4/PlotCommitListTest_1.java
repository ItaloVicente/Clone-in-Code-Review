	private static Set<Integer> asSet(int... numbers) {
		Set<Integer> result = new HashSet<Integer>();
		for (int n : numbers)
			result.add(Integer.valueOf(n));
		return result;
	}

