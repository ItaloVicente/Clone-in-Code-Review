			final IResource resource = selectedResources[0];
			if (!(resource instanceof IFile)) {
				return false;
			}
			final RepositoryMapping mapping = RepositoryMapping
					.getMapping(resource.getProject());
			return mapping != null;
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
