	private boolean isPartPlaceholderInPerspectiveToBeRendered(MPart mPart) {
		MPlaceholder curSharedRef = mPart.getCurSharedRef();
		boolean toBeRendered = false;
		if (curSharedRef != null) {
			toBeRendered = curSharedRef.isToBeRendered();
			if (!toBeRendered) {
				return false;
			}
		}
		boolean placeholderToBeRendered = false;
		MPlaceholder mPlaceholder = modelService.findPlaceholderFor(window, mPart);
		if (mPlaceholder != null) {
			placeholderToBeRendered = mPlaceholder.isToBeRendered();
			if (!placeholderToBeRendered) {
				return false;
			}
		}
		return toBeRendered || placeholderToBeRendered;
	}

