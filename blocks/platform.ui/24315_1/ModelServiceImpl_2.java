				MToolBar toolBar = part.getToolbar();
				if (toolBar != null) {
					findElementsRecursive(toolBar, matcher, elements, searchFlags);
				}
			}
			for (MHandler child : part.getHandlers()) {
				findElementsRecursive(child, matcher, elements, searchFlags);
