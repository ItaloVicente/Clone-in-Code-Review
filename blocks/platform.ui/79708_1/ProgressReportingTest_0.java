	@Override
	protected void doTearDown() throws Exception {
		boolean newRunInBackgroundSetting = oldRunInBackgroundSetting;
		setRunInBackground(newRunInBackgroundSetting);
		super.doTearDown();
	}

	private void setRunInBackground(boolean newRunInBackgroundSetting) {
		WorkbenchPlugin.getDefault().getPreferenceStore().setValue(IPreferenceConstants.RUN_IN_BACKGROUND,
				newRunInBackgroundSetting);
	}

