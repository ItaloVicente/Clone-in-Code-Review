	@Inject
	@Optional
	private void subscribeTopicChildrenRemoved(@UIEventTopic(ElementContainer.TOPIC_CHILDREN) Event event) {

		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenu)) {
			return;
		}

		if (!UIEvents.isREMOVE(event)) {
			return;
		}

		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);// MMEnu
		Object oldValue = event.getProperty(UIEvents.EventTags.OLD_VALUE);// MenuElement

		MMenu menuModel = (MMenu) element;

		if (oldValue instanceof MMenu) {
			MMenu oldMMenu = (MMenu) oldValue;
			handleMenuElementRemove(menuModel, oldMMenu);
		}
		else if (oldValue instanceof MMenuElement) {
			MMenuElement oldMMenuElement = (MMenuElement) oldValue;
			handleMenuElementRemove(menuModel, oldMMenuElement);
		}
		else if (oldValue instanceof List) {
			@SuppressWarnings("rawtypes")
			List oldList = (List) oldValue;
			handleMenuElementRemove(menuModel, oldList);
		}
		else{
			throw new RuntimeException("Deleted menu element type is not matching"); //$NON-NLS-1$
		}

	}

	void handleMenuElementRemove(MMenu menuModel, MMenu oldValue) {
		if (menuModel.getLabel() == null){
			return;
		}
		MenuManager menuManager = getManager(menuModel);
		menuManager.remove(getManager(oldValue));
		menuManager.update(false);
	}

	void handleMenuElementRemove(MMenu menuModel, MMenuElement oldValue) {
		if (menuModel.getLabel() == null){
			return;
		}
		MenuManager menuManager = getManager(menuModel);
		IContributionItem contribution = getContribution(oldValue);
		menuManager.remove(contribution);
		menuManager.update(false);
	}

	void handleMenuElementRemove(MMenu menuModel, @SuppressWarnings("rawtypes") List list) {
		MenuManager menuManager = getManager(menuModel);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object item = list.get(i);
				if (item instanceof MMenu) {
				} else if (item instanceof MMenuElement) {
					MMenuElement iMenuElement = (MMenuElement) item;
					IContributionItem contribution = getContribution(iMenuElement);
					menuManager.remove(contribution);
					menuManager.update(true);
				} else {
				}
			}
		}
	}

