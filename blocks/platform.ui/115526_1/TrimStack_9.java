		horizontalItem.addListener(SWT.Selection, event -> {
			if (!minimizedElement.getTags().contains(IPresentationEngine.ORIENTATION_HORIZONTAL)) {
				minimizedElement.getTags().remove(IPresentationEngine.ORIENTATION_VERTICAL);
				minimizedElement.getTags().add(IPresentationEngine.ORIENTATION_HORIZONTAL);
				if (isShowing) {
					setPaneLocation();
