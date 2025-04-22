		cleanUp(toolbarModel, getWindow());
	}

	@Override
	protected void cleanUp(MToolBar toolbarModel, MWindow window) {
		Collection<ToolBarContributionRecord> vals = getModelContributionToRecord(
				window).values();
