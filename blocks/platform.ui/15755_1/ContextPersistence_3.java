				contextDefinitionCount, contextManager, contextsToConfiguration);
	}

	@Override
	public void dispose() {
		contextsToConfiguration.clear();
		super.dispose();
