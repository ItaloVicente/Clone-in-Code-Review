					Display.getCurrent().asyncExec(new Runnable() {
						@Override
						public void run() {
							int visCount = modelService.countRenderableChildren(theContainer);
							if (!isLastEditorStack(theContainer) && visCount == 0) {
								theContainer.setToBeRendered(false);
							}
