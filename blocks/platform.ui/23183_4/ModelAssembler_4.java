		MModelFragments fragmentsContainer = (MModelFragments) extensionRoot;
		List<MModelFragment> fragments = fragmentsContainer.getFragments();
		boolean evalImports = false;
		for (MModelFragment fragment : fragments) {
			List<MApplicationElement> elements = fragment.getElements();
			if (elements.size() == 0) {
				continue;
			}
