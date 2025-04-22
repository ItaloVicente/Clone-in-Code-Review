			if (resource instanceof IContainer) {
				CompareTreeView view;
				try {
					view = (CompareTreeView) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.showView(CompareTreeView.ID);
					view.setInput(resource, dlg.getRefName());
				} catch (PartInitException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
			}
