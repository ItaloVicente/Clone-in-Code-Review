	@Inject
	@Optional
	private void subscribeTopicAppStartup(
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event) {
		List<MToolBar> toolBars = modelService.findElements(application, null,
				MToolBar.class, null);
		for (MToolBar mToolBar : toolBars) {
			if (mToolBar.getTags().contains(HIDDEN_BY_USER)) {
				mToolBar.setVisible(false);
				mToolBar.setToBeRendered(false);
			}
		}
	}

