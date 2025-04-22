		Display.getCurrent().asyncExec(() -> {
			int tbrCount = modelService.toBeRenderedCount(container);

			boolean lastStack = isLastEditorStack(container);
			if (tbrCount == 0 && !lastStack) {
				container.setToBeRendered(false);
			}

			MElementContainer<?> lclContainer = container;
			if (lclContainer.getChildren().size() == 0) {
				MElementContainer<MUIElement> parent = container.getParent();
				if (parent != null && !lastStack) {
