			return testRepositoryProperties(repository, args);

		} else if ("fileOrFolderInRepository".equals(property)) { //$NON-NLS-1$
			if (collection.size() != 1)
				return false;

			IStructuredSelection selection = getStructuredSelection(collection);
			if (selection.size() != 1)
				return false;

			Object firstElement = selection.getFirstElement();
			IResource resource = AdapterUtils.adapt(firstElement,
					IResource.class);
			if (resource instanceof IFile || resource instanceof IFolder) {
				RepositoryMapping m = RepositoryMapping.getMapping(resource);
				if (m != null)
					return testRepositoryProperties(m.getRepository(), args);
			}
