		disposeColors(stringToColor.values().iterator());
		disposeColors(staleColors.iterator());
		stringToColor.clear();
		staleColors.clear();
		display = null;
	}

	@Override
