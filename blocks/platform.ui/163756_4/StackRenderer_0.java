		MPlaceholder curSharedRef = part.getCurSharedRef();
		if (curSharedRef != null) {
			if (curSharedRef.getTags().contains(IPresentationEngine.NO_CLOSE)) {
				return false;
			}
			return curSharedRef.isCloseable();
