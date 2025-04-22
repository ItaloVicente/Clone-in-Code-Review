			Repository repository = getRepositoryOfProjects(collection, false);
			return repository != null;

		} else if ("resourcesSingleRepository".equals(property)) { //$NON-NLS-1$
			if (collection.isEmpty())
				return false;

			Object firstElement = collection.iterator().next();
			IStructuredSelection selection;
			if (collection.size() == 1 && firstElement instanceof ITextSelection) {
				selection = SelectionUtils.getStructuredSelection((ITextSelection) firstElement);
			} else {
				selection = new StructuredSelection(new ArrayList<Object>(
						collection));
			}

			IResource[] resources = SelectionUtils
					.getSelectedResources(selection);
			Repository repository = getRepositoryOfResources(resources);
