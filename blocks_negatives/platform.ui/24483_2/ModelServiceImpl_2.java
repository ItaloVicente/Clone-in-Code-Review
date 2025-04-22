			} else {
				children.addAll(app.getHandlers());
				children.addAll(app.getCommands());
				children.addAll(app.getBindingContexts());
				children.addAll(app.getBindingTables());
			}
			
			for (MApplicationElement child : children) {
				findElementsRecursive(child, clazz, matcher, elements, searchFlags);
