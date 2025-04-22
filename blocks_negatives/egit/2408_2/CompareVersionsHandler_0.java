				CompareTreeView view;
				try {
					view = (CompareTreeView) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.showView(CompareTreeView.ID);
					view.setInput(new IResource[] { (IResource) input },
							commit1.getId().name(), commit2.getId().name());
				} catch (PartInitException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
