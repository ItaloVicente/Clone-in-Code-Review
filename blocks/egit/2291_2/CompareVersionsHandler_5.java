			} else if (input instanceof IResource) {
				CompareTreeView view;
				try {
					view = (CompareTreeView) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.showView(CompareTreeView.ID);
					view.setInput((IResource) input, commit1.getId().name(),
							commit2.getId().name());
				} catch (PartInitException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
			} else if (input == null) {
				CompareTreeView view;
				try {
					view = (CompareTreeView) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.showView(CompareTreeView.ID);
					view.setInput(repository, commit1.getId().name(), commit2
							.getId().name());
				} catch (PartInitException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
