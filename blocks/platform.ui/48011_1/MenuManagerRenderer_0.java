	@SuppressWarnings("unchecked")
	@Inject
	@Optional
	private void subscribeTopicChildrenRemoved(@UIEventTopic(ElementContainer.TOPIC_CHILDREN) Event event) {

		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenu)) {
			return;
		}

		if (!UIEvents.isREMOVE(event)) {
			return;
		}

		Object type = event.getProperty(UIEvents.EventTags.TYPE);
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);// MMEnu
		Object oldValue = event.getProperty(UIEvents.EventTags.OLD_VALUE);// MenuElement

		MMenu menuModel = (MMenu) element;
		MenuManager menuManager = getManager(menuModel);

		System.out.println(String.format("%n%n ----- [%s] ----- ", type)); //$NON-NLS-1$
		System.out.println(describe(element));
		System.out.println(describe(oldValue));


		if (oldValue instanceof MMenu) {
			MMenu oldMMenu = (MMenu) oldValue;
			handleMenuElementRemove(menuModel, menuManager, oldMMenu);
		}
		else if (oldValue instanceof MMenuElement) {
			MMenuElement oldMMenuElement = (MMenuElement) oldValue;
			handleMenuElementRemove(menuModel, menuManager, oldMMenuElement);
		}
		else if (oldValue instanceof List) {
			List oldList = (List) oldValue;
			handleMenuElementRemove(menuModel, menuManager, oldList);
		}
		else{
			throw new RuntimeException("Deleted menu element type is not matching"); //$NON-NLS-1$
		}

	}

	void handleMenuElementRemove(MMenu menuModel, MenuManager menuManager, MMenu oldValue){
		if (menuModel.getLabel() == null)
			return;
		menuManager.remove(getManager(oldValue));
		menuManager.update(false);
	}

	void handleMenuElementRemove(MMenu menuModel, MenuManager menuManager, MMenuElement oldValue){
		if (menuModel.getLabel() == null)
			return;
		IContributionItem contribution = getContribution(oldValue);
		System.out.println(String.format(" ---> %s ", contribution)); //$NON-NLS-1$
		menuManager.remove(contribution);
		menuManager.update(false);
	}

	void handleMenuElementRemove(MMenu menuModel, MenuManager menuManager, List list){
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				Object item = list.get(i);
				String descr = (item == null) ? "NULL" : describe(item);//$NON-NLS-1$
				System.out.print(String.format("       >>>> %s", descr));//$NON-NLS-1$
				if (item instanceof MMenu) {
					MMenu iMenu = (MMenu) item;
					System.out.println("   >>>> is MMenu: " + iMenu);//$NON-NLS-1$
				} else if (item instanceof MMenuElement) {
					MMenuElement iMenuElement = (MMenuElement) item;
					System.out.println("   >>>> is MMenuElement: " + iMenuElement);//$NON-NLS-1$
					IContributionItem contribution = getContribution(iMenuElement);
					menuManager.remove(contribution);
					menuManager.update(true);
				} else {
					throw new RuntimeException("Deleted menu element type is not matching"); //$NON-NLS-1$
				}
			}
			System.out.println("List");//$NON-NLS-1$
		}
	}

	String describe(Object obj) {
		String flags = "   "; //$NON-NLS-1$
		String desc = null;
		if(obj instanceof List){
			List l = (List) obj;
			desc = String.format("[List: %s] - %s", l.size() , obj); //$NON-NLS-1$
		} else if (obj instanceof MMenu){
			MMenu menuModel = (MMenu) obj;
			flags = String.format("%s%s%s", (menuModel.isToBeRendered() ? "R" : " "), //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
					(menuModel.isVisible() ? "V" : " "), //$NON-NLS-1$//$NON-NLS-2$
					(menuModel.isEnabled() ? "E" : " ")); //$NON-NLS-1$//$NON-NLS-2$
			desc = String.format("[MMenu: %s: %s] (%s) ID:%s  ", //$NON-NLS-1$
					menuModel.getClass().getSimpleName(), menuModel.getLabel(), flags, menuModel.getElementId());
		} else if (obj instanceof MMenuElement){
			MMenuElement menuElement = (MMenuElement) obj;
			flags = String.format("%s%s", (menuElement.isToBeRendered() ? "R" : " "), //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
					(menuElement.isVisible() ? "V" : " ")); //$NON-NLS-1$//$NON-NLS-2$
			desc = String.format("[MMenuElement: %s: %s] (%s) ID:%s  ", //$NON-NLS-1$
					menuElement.getClass().getSimpleName(), menuElement.getLabel(), flags, menuElement.getElementId());
		}
		return desc;
	}


