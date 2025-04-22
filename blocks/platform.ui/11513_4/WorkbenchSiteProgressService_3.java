
	protected void showBusy(boolean busy) {
		MPart part = site.getModel();
		int tagsListCount = part.getTags().size();
		boolean containsBusyTag = part.getTags().contains(CSSConstants.CSS_BUSY_CLASS);

		if (busy && !containsBusyTag) {
			part.getTags().add(CSSConstants.CSS_BUSY_CLASS);
		} else if (!busy && containsBusyTag) {
			part.getTags().remove(CSSConstants.CSS_BUSY_CLASS);
		}

		if (part.getTags().size() != tagsListCount) {
			UIEvents.publishEvent(createEvent(UIEvents.UILabel.TOPIC_BUSY)
					.withParam(UIEvents.EventTags.ELEMENT, part)
					.withParam(UIEvents.EventTags.ATTNAME, UIEvents.UILifeCycle.BUSY)
					.withParam(UIEvents.EventTags.NEW_VALUE, busy));
		}
	}
