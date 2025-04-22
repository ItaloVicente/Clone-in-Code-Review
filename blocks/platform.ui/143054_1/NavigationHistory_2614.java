		INavigationLocation result[] = new INavigationLocation[history.size()];
		for (int i = 0; i < result.length; i++) {
			NavigationHistoryEntry e = (NavigationHistoryEntry) history.get(i);
			result[i] = e.location;
		}
		return result;
	}

	@Override
