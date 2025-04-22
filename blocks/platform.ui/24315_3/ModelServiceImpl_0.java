		if (searchRoot instanceof MApplication && (searchFlags == ANYWHERE)) {
			MApplication app = (MApplication) searchRoot;

			for (MHandler child : app.getHandlers()) {
				findElementsRecursive(child, clazz, matcher, elements, searchFlags);
			}

			for (MCommand command : app.getCommands()) {
				findElementsRecursive(command, clazz, matcher, elements, searchFlags);
			}

			for (MBindingContext bindingContext : app.getBindingContexts()) {
				findElementsRecursive(bindingContext, clazz, matcher, elements, searchFlags);
			}

			for (MBindingTable bindingTable : app.getBindingTables()) {
				findElementsRecursive(bindingTable, clazz, matcher, elements, searchFlags);
			}
		}

		if (searchRoot instanceof MBindingContext && (searchFlags == ANYWHERE)) {
			MBindingContext bindingContext = (MBindingContext) searchRoot;
			for (MBindingContext child : bindingContext.getChildren()) {
				findElementsRecursive(child, clazz, matcher, elements, searchFlags);
			}
		}

