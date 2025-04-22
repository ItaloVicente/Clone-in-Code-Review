		}
		if (resource instanceof IContainer){
			CompareTreeView view;
			try {
				view = (CompareTreeView) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.showView(CompareTreeView.ID);
				view.setInput(resource, CompareTreeView.INDEX_VERSION);
			} catch (PartInitException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
		}
