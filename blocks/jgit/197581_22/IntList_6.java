	public static IntList filledWithRange(int start
		IntList list = new IntList(end - start);
		for (int val = start; val < end; val++) {
			list.add(val);
		}
		return list;
	}

