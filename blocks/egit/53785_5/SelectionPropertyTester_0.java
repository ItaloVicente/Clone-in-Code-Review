		} else if ("resourcesAllInRepository".equals(property)) { //$NON-NLS-1$
			IStructuredSelection selection = getStructuredSelection(collection);

			IResource[] resources = SelectionUtils
					.getSelectedResources(selection);
			return haveRepositories(resources);
