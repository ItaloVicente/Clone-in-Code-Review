	private static ArrayList<String> filterOut(ArrayList<String> strings
			IntList indicesToRemove) {
		int n = indicesToRemove.size();
		if (n == strings.size()) {
			return new ArrayList<>(0);
		}
		switch (n) {
		case 0:
			return strings;
		case 1:
			strings.remove(indicesToRemove.get(0));
			return strings;
		default:
			int length = strings.size();
			ArrayList<String> result = new ArrayList<>(length - n);
			int j = n - 1;
			int idx = indicesToRemove.get(j);
			for (int i = 0; i < length; i++) {
				if (i == idx) {
					idx = (--j >= 0) ? indicesToRemove.get(j) : -1;
				} else {
					result.add(strings.get(i));
				}
			}
			return result;
		}
	}

