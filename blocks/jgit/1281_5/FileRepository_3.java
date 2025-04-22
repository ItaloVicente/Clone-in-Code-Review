	@Override
	protected void doClose() {
		configChangeListenerHandle.remove();
		super.doClose();
	}

