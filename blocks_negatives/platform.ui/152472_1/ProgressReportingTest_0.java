	@Override
	protected void doTearDown() throws Exception {
		boolean newRunInBackgroundSetting = oldRunInBackgroundSetting;
		setRunInBackground(newRunInBackgroundSetting);
		super.doTearDown();
	}

