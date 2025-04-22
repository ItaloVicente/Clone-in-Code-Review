		} else {
			CompareTreeView view;
			try {
				view = (CompareTreeView) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().showView(
								CompareTreeView.ID);
				try {
					view.setInput(resources, repository.resolve(Constants.HEAD)
							.name());
				} catch (IOException e) {
					Activator.handleError(e.getMessage(), e, true);
					return null;
				}
			} catch (PartInitException e) {
				Activator.handleError(e.getMessage(), e, true);
				return null;
			}
			return null;
		}
