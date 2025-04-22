	private void activateContextService() {
		IContextService contextService = CommonUtils.getService(getSite(), IContextService.class);
		if (contextService != null) {
			contextService.activateContext(VIEW_ID);
		}
	}

