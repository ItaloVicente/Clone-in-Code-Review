		String activeByDefaultString = configElement.getAttribute(ATT_ACTIVE_BY_DEFAULT);
		activeByDefault = (activeByDefaultString != null && activeByDefaultString.length() > 0)
				? Boolean.valueOf(activeByDefaultString).booleanValue() : true;

		String providesSaveablesString = configElement.getAttribute(ATT_PROVIDES_SAVEABLES);
		providesSaveables = (providesSaveablesString != null && providesSaveablesString.length() > 0)
				? Boolean.valueOf(providesSaveablesString).booleanValue() : false;
