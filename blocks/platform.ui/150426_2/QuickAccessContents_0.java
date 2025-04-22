	private static QuickAccessElement[] putPrefixMatchFirst(QuickAccessElement[] elements, String prefix) {
		QuickAccessElement[] res = new QuickAccessElement[elements.length];
		List<Integer> matchingIndexes = new ArrayList<>();
		for (int i = 0; i < elements.length; i++) {
			if (elements[i].getLabel().toLowerCase().startsWith(prefix.toLowerCase())) {
				matchingIndexes.add(Integer.valueOf(i));
			}
		}
		int currentMatchIndex = 0;
		int currentNonMatchIndex = matchingIndexes.size();
		for (int i = 0; i < res.length; i++) {
			boolean isMatch = !matchingIndexes.isEmpty() && matchingIndexes.iterator().next().intValue() == i;
			if (isMatch) {
				matchingIndexes.remove(0);
				res[currentMatchIndex] = elements[i];
				currentMatchIndex++;
			} else {
				res[currentNonMatchIndex] = elements[i];
				currentNonMatchIndex++;
			}
		}
		return res;
	}

