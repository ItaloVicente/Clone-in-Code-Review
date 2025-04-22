	static final GroupFilterConfigurationArea generateScopeArea(IConfigurationElement configurationElement) {
		int buttonsEnabled = 0;

		if (configurationElement != null) {
			boolean onAny = Boolean.valueOf(configurationElement.getAttribute(ATTRIBUTE_ON_ANY));
			boolean onAnyInContainer = Boolean
					.valueOf(configurationElement.getAttribute(ATTRIBUTE_ON_ANY_IN_SAME_CONTAINER));
			boolean onSelectedAndChildren = Boolean
					.valueOf(configurationElement.getAttribute(ATTRIBUTE_ON_SELECTED_AND_CHILDREN));
			boolean onSelectedOnly = Boolean.valueOf(configurationElement.getAttribute(ATTRIBUTE_ON_SELECTED_ONLY));
			boolean onWorkingSet = Boolean.valueOf(configurationElement.getAttribute(ATTRIBUTE_ON_WORKING_SET));

			buttonsEnabled |= onAny ? ScopeArea.BUTTON_ON_ANY : 0;
			buttonsEnabled |= onAnyInContainer ? ScopeArea.BUTTON_ON_ANY_IN_SAME_CONTAINER : 0;
			buttonsEnabled |= onSelectedAndChildren ? ScopeArea.BUTTON_ON_SELECTED_AND_CHILDREN : 0;
			buttonsEnabled |= onSelectedOnly ? ScopeArea.BUTTON_ON_SELECTED_ONLY : 0;
			buttonsEnabled |= onWorkingSet ? ScopeArea.BUTTON_ON_WORKING_SET : 0;
		}

		if (buttonsEnabled == 0)
			buttonsEnabled = ScopeArea.ALL_BUTTONS_ENABLED;

		return new ScopeArea(buttonsEnabled);
	}

