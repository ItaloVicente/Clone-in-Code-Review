	private void restoreSashFormWeights() {
		if (memento != null) {
			restoreSashFormWeights(horizontalSashForm,
					MEMENTO_HORIZONTAL_SASH_FORM_WEIGHT);
			restoreSashFormWeights(stagingSashForm,
					MEMENTO_STAGING_SASH_FORM_WEIGHT);
		}
	}

	private void restoreSashFormWeights(SashForm sashForm, String mementoKey) {
		String weights = memento.getString(mementoKey);
		if (weights != null && !weights.isEmpty()) {
			sashForm.setWeights(stringToIntArray(weights));
		}
	}

	private static int[] stringToIntArray(String s) {
		String[] parts = s.split(","); //$NON-NLS-1$
		int[] ints = new int[parts.length];
		for (int i = 0; i < parts.length; i++) {
			ints[i] = Integer.valueOf(parts[i]).intValue();
		}
		return ints;
	}

