	void setForceUpdate(boolean force) {
		forceUpdateSelected = force;
		if (forceUpdateButton != null) {
			forceUpdateButton.setSelection(force);
		}
	}

