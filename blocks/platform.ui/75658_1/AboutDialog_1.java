	@Override
    public int open() {
		if (contextService != null) {
			contextActivation = contextService.activateContext(ID_CONTEXT);
		}
		return super.open();
    }

