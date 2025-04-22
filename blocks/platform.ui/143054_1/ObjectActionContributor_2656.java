		for (int i = 0; i < currentContribution.menus.size(); i++) {
			IConfigurationElement menuElement = (IConfigurationElement) currentContribution.menus.get(i);
			currentContribution.contributeMenu(menuElement, menu, true);
		}
		return true;
	}

	@Override
	protected ActionDescriptor createActionDescriptor(IConfigurationElement element) {
		return new ActionDescriptor(element, ActionDescriptor.T_POPUP);
	}

	@Override
