	private boolean shouldNotRenderPart(MPart part) {
		if (!part.isToBeRendered()) {
			return true;
		}
		MPlaceholder curSharedRef = part.getCurSharedRef();
		if (curSharedRef != null && !curSharedRef.isToBeRendered()) {
			return true;
		}
		MPlaceholder mPlaceholder = modelService.findPlaceholderFor(window, part);
		if (mPlaceholder != null && !mPlaceholder.isToBeRendered()) {
			return true;
		}
		return false;
	}

