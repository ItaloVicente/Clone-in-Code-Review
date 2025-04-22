			List<MApplicationElement> children = new ArrayList<MApplicationElement>();
			if (clazz != null) {
				if (clazz.equals(MHandler.class)) {
					children.addAll(app.getHandlers());
				} else if (clazz.equals(MCommand.class)) {
					children.addAll(app.getCommands());
				} else if (clazz.equals(MBindingContext.class)) {
					children.addAll(app.getBindingContexts());
				} else if (clazz.equals(MBindingTable.class)) {
					children.addAll(app.getBindingTables());
				}
			} else {
				children.addAll(app.getHandlers());
				children.addAll(app.getCommands());
				children.addAll(app.getBindingContexts());
				children.addAll(app.getBindingTables());
			}
			
			for (MApplicationElement child : children) {
				findElementsRecursive(child, clazz, matcher, elements, searchFlags);
			}
		}
