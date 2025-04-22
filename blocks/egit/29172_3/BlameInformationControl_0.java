		if (input == null) {
			this.revision = null;
			if (showAnnotationsLink != null) {
				if (!showAnnotationsLink.isDisposed())
					showAnnotationsLink
							.removeSelectionListener(showAnnotationsLinkSelectionAdapter);
				showAnnotationsLink = null;
				showAnnotationsLinkSelectionAdapter = null;
			}
			return;
		}

