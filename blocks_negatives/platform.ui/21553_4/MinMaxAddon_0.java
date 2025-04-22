		if (isEmptyPerspectiveStack(element)) {
			element.setVisible(true);
			element.getTags().remove(MINIMIZED_BY_ZOOM);
			return;
		}

