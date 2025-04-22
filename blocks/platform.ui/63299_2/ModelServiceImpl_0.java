			handleNullRefPlaceHolders(element, refWin, true);
		}

		return element;
	}

	private void handleNullRefPlaceHolders(MUIElement element, MWindow refWin, boolean resolveAlways) {
		EPlaceholderResolver resolver = appContext.get(EPlaceholderResolver.class);
		List<MPlaceholder> phList = findElements(element, null, MPlaceholder.class, null);
		List<MPlaceholder> nullRefList = new ArrayList<MPlaceholder>();
		for (MPlaceholder ph : phList) {
			if (resolveAlways) {
				resolver.resolvePlaceholderRef(ph, refWin);
			} else if ((!resolveAlways) && (ph.getRef() == null)) {
