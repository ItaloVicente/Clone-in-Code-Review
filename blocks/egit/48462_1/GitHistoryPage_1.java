			Object useAsInput = object;

			ISelection selection = getSite().getPage().getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				Set<IResource> selectedResources = SelectionUtils
						.getSelectedResourcesSet(structuredSelection);
				if (selectedResources.contains(object)) {
					Repository repository = SelectionUtils
							.getRepository(structuredSelection);
					if (repository != null) {
						IResource[] resourceArray = selectedResources
								.toArray(new IResource[selectedResources.size()]);
						useAsInput = new HistoryPageInput(repository,
								resourceArray);
					}
				}
			}

