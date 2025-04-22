		if (contentProvider != null) {
			try {
				contentProvider.inputChanged(this, getInput(), null);
			} catch (Exception e) {
			}
			contentProvider.dispose();
			contentProvider = null;
		}
		if (labelProvider != null) {
			labelProvider.removeListener(labelProviderListener);
			labelProvider.dispose();
			labelProvider = null;
		}
		input = null;
