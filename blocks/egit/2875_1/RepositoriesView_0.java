	private void activateContextService() {
		IContextService contextService = (IContextService) getSite()
				.getService(IContextService.class);
		if (contextService != null) {
			contextService.activateContext(VIEW_ID);
		}
	}

