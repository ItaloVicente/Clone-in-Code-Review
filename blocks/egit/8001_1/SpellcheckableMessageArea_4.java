		int size = wrapOffsets.size();
		if (size == 0) {
			return null;
		} else {
			int[] result = new int[size];
			for (int i= 0; i < size; i++) {
				result[i] = wrapOffsets.get(i);
			}
			return result;
