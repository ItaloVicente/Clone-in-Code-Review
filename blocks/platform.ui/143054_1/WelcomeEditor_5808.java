		super.dispose();
		if (this.colorListener != null) {
			JFacePreferences.getPreferenceStore().removePropertyChangeListener(
					this.colorListener);
		}
	}

	@Override
