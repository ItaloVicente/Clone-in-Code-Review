		if (searchRoot instanceof MApplication && (searchFlags == ANYWHERE)) {
			MApplication app = (MApplication) searchRoot;

			for (MHandler child : app.getHandlers()) {
				findElementsRecursive(child, matcher, elements, searchFlags);
			}

			for (MCommand command : app.getCommands()) {
				findElementsRecursive(command, matcher, elements, searchFlags);
			}
		}
		
