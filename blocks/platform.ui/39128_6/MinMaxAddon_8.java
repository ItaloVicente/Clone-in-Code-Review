		if (element instanceof MPartStack) {
			MArea area = getAreaFor((MPartStack) element);
			if (area != null && area.getTags().contains(MAXIMIZEABLE_CHILDREN_TAG)) {
				MPlaceholder placeholder = area.getCurSharedRef();
				ignoreTagChanges = true;
				try {
					placeholder.getTags().remove(MAXIMIZED);
				} finally {
					ignoreTagChanges = false;
				}
				adjustCTFButtons(placeholder);
			}
		}

