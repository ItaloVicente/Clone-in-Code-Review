	public ICommandImageService getCommandImageService() {
		if (commandImageService == null) {
			commandImageService = context.get(ICommandImageService.class);
		}
		return commandImageService;
	}

