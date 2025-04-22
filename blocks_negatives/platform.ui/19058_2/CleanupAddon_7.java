				if (container.getTags().contains(IPresentationEngine.NO_AUTO_COLLAPSE)) {
					return;
				}

				int visCount = modelService.countRenderableChildren(container);

				final MElementContainer<MUIElement> theContainer = container;
				if (visCount == 0) {
					Display.getCurrent().asyncExec(new Runnable() {
						public void run() {
							int visCount = modelService.countRenderableChildren(theContainer);
							if (!isLastEditorStack(theContainer) && visCount == 0)
								theContainer.setToBeRendered(false);
						}
					});
				} else {
					boolean makeInvisible = true;

					for (MUIElement kid : container.getChildren()) {
						if (!kid.isToBeRendered())
							continue;
						if (kid.isVisible()) {
							makeInvisible = false;
							break;
						}
