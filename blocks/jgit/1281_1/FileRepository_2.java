	@Override
	protected void doClose() {
		getConfig().removeChangeListener(configChangeListener);
		super.doClose();
	}

