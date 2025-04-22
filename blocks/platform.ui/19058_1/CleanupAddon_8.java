			int visCount = modelService.countRenderableChildren(container);

			final MElementContainer<MUIElement> theContainer = container;
			if (visCount == 0) {
				sync.asyncExec(new Runnable() {
					public void run() {
						int visCount = modelService.countRenderableChildren(theContainer);
						if (!isLastEditorStack(theContainer) && visCount == 0)
							theContainer.setToBeRendered(false);
					}
				});
