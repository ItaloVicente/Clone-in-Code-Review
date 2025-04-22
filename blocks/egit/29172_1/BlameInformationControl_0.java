		if (input == null) {
			this.revision = null;
			if (showAnnotationsLink != null) {
				showAnnotationsLink
						.removeSelectionListener(showAnnotationsLinkSelectionAdapter);
				showAnnotationsLink = null;
				showAnnotationsLinkSelectionAdapter = null;
			}
			return;
		}

