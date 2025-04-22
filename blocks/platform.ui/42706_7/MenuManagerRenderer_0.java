	@Inject
	@Optional
	private void subscribeTopicUpdateMenuEnablement(
			@UIEventTopic(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC) Event eventData) {

		for (Map.Entry<IContributionItem, MMenuElement> entry : contributionToModel.entrySet()) {
			processVisibility(entry.getKey(), entry.getValue());
		}
		for (MenuManager mgr : managerToModel.keySet()) {
			mgr.update(false);
		}

	}

