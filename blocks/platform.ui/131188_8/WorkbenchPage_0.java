	private boolean isPartPlaceholderInPerspectiveToBeRendered(MPart mPart) {
		MPlaceholder curSharedRef = mPart.getCurSharedRef();
		if (mPart.getCurSharedRef() != null && !curSharedRef.isToBeRendered()) {
			return false;
		}
		MPlaceholder mPlaceholder = modelService.findPlaceholderFor(window, mPart);
		if (mPlaceholder != null && !mPlaceholder.isToBeRendered()) {
			return false;
		}
		return true;
	}

