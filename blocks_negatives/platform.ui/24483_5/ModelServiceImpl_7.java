		if (searchRoot instanceof MPerspective) {
			MPerspective persp = (MPerspective) searchRoot;
			for (MWindow dw : persp.getWindows()) {
				findElementsRecursive(dw, clazz, matcher, elements, searchFlags);
			}
		}
		if (searchRoot instanceof MPlaceholder) {
			MPlaceholder ph = (MPlaceholder) searchRoot;
