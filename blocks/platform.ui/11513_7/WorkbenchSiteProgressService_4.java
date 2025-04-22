
	protected void showBusy(boolean busy) {
		MPart part = site.getModel();
		boolean containsBusyTag = part.getTags().contains(CSSConstants.CSS_BUSY_CLASS);

		if (busy && !containsBusyTag) {
			part.getTags().add(CSSConstants.CSS_BUSY_CLASS);
		} else if (!busy && containsBusyTag) {
			part.getTags().remove(CSSConstants.CSS_BUSY_CLASS);
		}
	}
