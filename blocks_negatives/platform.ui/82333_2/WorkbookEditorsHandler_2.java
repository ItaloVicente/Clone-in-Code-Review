		MPartStack activeStack = getActiveStack(uiElement);
		if (activeStack != null) {
			if (activeStack.getRenderer() instanceof StackRenderer
					&& activeStack.getWidget() instanceof CTabFolder) {
				StackRenderer stackRenderer = (StackRenderer) activeStack.getRenderer();
				stackRenderer.showAvailableItems(activeStack, (CTabFolder) activeStack.getWidget(), true);
