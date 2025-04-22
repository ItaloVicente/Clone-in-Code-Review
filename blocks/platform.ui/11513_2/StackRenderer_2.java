		} else if (UIEvents.UILifeCycle.BUSY.equals(attName)) {
			updateBusyIndicator(cti, part, (Boolean) newValue);
		}
	}

	@SuppressWarnings("restriction")
	protected void updateBusyIndicator(CTabItem cti, MPart part, boolean busy) {
		int tagsListCount = part.getTags().size();
		boolean containsBusyTag = part.getTags().contains(
				CSSConstants.CSS_BUSY_CLASS);

		if (busy && !containsBusyTag) {
			part.getTags().add(CSSConstants.CSS_BUSY_CLASS);
		} else if (!busy && containsBusyTag) {
			part.getTags().remove(CSSConstants.CSS_BUSY_CLASS);
		}
		if (part.getTags().size() != tagsListCount) {
			setCSSInfo(part, cti);
			reapplyStyles(cti);
