		if (curParent == null) {
			MWindow win = getTopLevelWindowFor(relTo);
			relTo = findPlaceholderFor(win, relTo);
			curParent = relTo.getParent();
		}
		Assert.isLegal(relTo != null && curParent != null);
