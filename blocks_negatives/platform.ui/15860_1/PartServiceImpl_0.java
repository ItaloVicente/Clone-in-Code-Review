	@Inject
	void setPart(@Optional @Named(IServiceConstants.ACTIVE_PART) MPart p) {
		if (activePart != p) {
			activate(p, true, true);
		}
	}

