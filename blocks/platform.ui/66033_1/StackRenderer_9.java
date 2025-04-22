	@Override
	protected boolean requiresFocus(MPart element) {
		MUIElement inStack = element.getCurSharedRef() != null ? element.getCurSharedRef() : element;
		if (inStack.getParent() != null && inStack.getParent().getTransientData().containsKey(INHIBIT_FOCUS)) {
			inStack.getParent().getTransientData().remove(INHIBIT_FOCUS);
			return false;
		}
