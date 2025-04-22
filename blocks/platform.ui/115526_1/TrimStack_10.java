		verticalItem.addListener(SWT.Selection, event -> {
			if (!minimizedElement.getTags().contains(IPresentationEngine.ORIENTATION_VERTICAL)) {
				minimizedElement.getTags().remove(IPresentationEngine.ORIENTATION_HORIZONTAL);
				minimizedElement.getTags().add(IPresentationEngine.ORIENTATION_VERTICAL);
				if (isShowing) {
					setPaneLocation();
