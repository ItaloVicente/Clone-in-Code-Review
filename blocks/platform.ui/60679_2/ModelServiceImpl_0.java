			handleNullRefPlaceHolders(element, refWin, true);
		}

		return element;
	}

	private void handleNullRefPlaceHolders(MUIElement element, MWindow refWin, boolean resolve) {
		EPlaceholderResolver resolver = appContext.get(EPlaceholderResolver.class);
		List<MPlaceholder> phList = findElements(element, null, MPlaceholder.class, null);
		List<MPlaceholder> nullRefList = new ArrayList<>();
		for (MPlaceholder ph : phList) {
			if (resolve) {
