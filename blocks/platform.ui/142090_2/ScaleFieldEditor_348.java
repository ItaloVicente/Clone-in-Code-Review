		getPreferenceStore()
				.setValue(getPreferenceName(), scale.getSelection());
	}

	public int getIncrement() {
		return incrementValue;
	}

	public int getMaximum() {
		return maxValue;
	}

	public int getMinimum() {
		return minValue;
	}

	@Override
