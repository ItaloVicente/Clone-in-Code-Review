		int nSize = colors.size();
		ColorDefinition[] retArray = new ColorDefinition[nSize];
		colors.toArray(retArray);
		Arrays.sort(retArray, ID_COMPARATOR);
		return retArray;
	}

	@Override
