	private void restoreSashFormWeights() {
		if (memento != null) {
			restoreSashFormWeights(horizontalSashForm,
					MEMENTO_HORIZONTAL_SASH_FORM_WEIGHT);
			restoreSashFormWeights(stagingSashForm,
					MEMENTO_STAGING_SASH_FORM_WEIGHT);
