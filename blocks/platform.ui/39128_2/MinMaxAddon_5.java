
		if (MPartStack.class.isInstance(element)) {
			MArea area = getAreaFor(MPartStack.class.cast(element));
			if (area != null && area.getTags().contains(MAXIMIZEABLE_CHILDREN_TAG)) {
				MPlaceholder placeholder = area.getCurSharedRef();
				ignoreTagChanges = true;
				placeholder.getTags().add(MAXIMIZED);
				ignoreTagChanges = false;
				adjustCTFButtons(placeholder);
			}
		}
