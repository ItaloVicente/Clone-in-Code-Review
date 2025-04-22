			}
			case "fileOrFolderInRepository": { //$NON-NLS-1$
				if (collection.size() != 1)
					return false;
				IStructuredSelection selection = getStructuredSelection(
						collection);
				if (selection.size() != 1)
					return false;
				Object firstElement = selection.getFirstElement();
				IResource resource = AdapterUtils
						.adaptToAnyResource(firstElement);
				if ((resource != null) && (resource instanceof IFile
						|| resource instanceof IFolder)) {
					RepositoryMapping m = RepositoryMapping
							.getMapping(resource);
					if (m != null) {
						if ((resource instanceof IFolder)
								&& resource.equals(m.getContainer())) {
							return false;
						} else {
							return testRepositoryProperties(m.getRepository(),
									args);
						}
