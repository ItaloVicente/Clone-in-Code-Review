	@Override
	public void saveState(IMemento viewMemento) {
		super.saveState(viewMemento);
		saveSashFormWeights(viewMemento);
	}

	private void saveSashFormWeights(IMemento viewMemento) {
		viewMemento.putString(MEMENTO_HORIZONTAL_SASH_FORM_WEIGHT,
				intArrayToString(horizontalSashForm.getWeights()));
		viewMemento.putString(MEMENTO_STAGING_SASH_FORM_WEIGHT,
				intArrayToString(stagingSashForm.getWeights()));
	}

	private static String intArrayToString(int[] ints) {
		StringBuilder res = new StringBuilder();
		if (ints != null && ints.length > 0) {
			res.append(String.valueOf(ints[0]));
			for (int i = 1; i < ints.length; i++) {
				res.append(',');
				res.append(String.valueOf(ints[i]));
			}
		}
		return res.toString();
	}

