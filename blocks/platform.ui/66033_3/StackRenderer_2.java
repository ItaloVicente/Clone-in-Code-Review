		String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
		Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);

		if (element.getParent() != null && element.getParent().getRenderer() == StackRenderer.this) {
			CTabItem cti = findItemForPart(part);
			if (cti != null) {
				updateTab(cti, part, attName, newValue);
			}
			return;
