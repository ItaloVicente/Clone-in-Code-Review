
		assertArrayEquals(new int[]{
				Integer.MIN_VALUE
		}
	}

	private int[] asInts(IntList l) {
		int[] result = new int[l.size()];
		for (int i = 0; i < l.size(); i++) {
			result[i] = l.get(i);
		}
		return result;
