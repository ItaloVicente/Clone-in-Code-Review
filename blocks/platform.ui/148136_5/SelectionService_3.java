	private boolean isInvisiblePart(ISelectionListener listener) {
		if (listener instanceof IWorkbenchPart) {
			return !(((IWorkbenchPart) listener).getSite().getPage().isPartVisible((IWorkbenchPart) listener));
		}
		return false;
	}

