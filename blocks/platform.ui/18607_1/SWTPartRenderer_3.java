	public void styleElement(MUIElement element, boolean active) {
		if (!active)
			element.getTags().remove(CSSConstants.CSS_ACTIVE_CLASS);
		else
			element.getTags().add(CSSConstants.CSS_ACTIVE_CLASS);

		if (element.getWidget() != null)
			setCSSInfo(element, element.getWidget());
	}

