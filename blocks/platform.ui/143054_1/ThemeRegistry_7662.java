		int nSize = themes.size();
		IThemeDescriptor[] retArray = new IThemeDescriptor[nSize];
		themes.toArray(retArray);
		Arrays.sort(retArray, ID_COMPARATOR);
		return retArray;
	}

	@Override
