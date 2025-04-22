				MToolBar toolBar = part.getToolbar();
				if (toolBar != null) {
					findElementsRecursive(toolBar, clazz, matcher, elements, searchFlags);
				}
			}
			for (MHandler child : part.getHandlers()) {
				findElementsRecursive(child, clazz, matcher, elements, searchFlags);
