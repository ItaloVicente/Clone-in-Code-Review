		MToolBarElement itemModel = (MToolBarElement) event.getProperty(UIEvents.EventTags.ELEMENT);
		IContributionItem ici = getContribution(itemModel);
		if (ici != null) {
			ici.update();
		}
	}

	@SuppressWarnings("unchecked")
	@Inject
	@Optional
	private void subscribeTopicChildAdded(@UIEventTopic(ElementContainer.TOPIC_CHILDREN) Event event) {
		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MToolBar)) {
			return;
		}
		MToolBar toolbarModel = (MToolBar) event.getProperty(UIEvents.EventTags.ELEMENT);
		if (UIEvents.isADD(event)) {
			Object obj = toolbarModel;
			processContents((MElementContainer<MUIElement>) obj);
		}
	}

	private HashSet<String> updateVariables = new HashSet<String>();

	@SuppressWarnings("unused")
