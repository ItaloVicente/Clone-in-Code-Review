		boolean useTreeCompare = shouldUseTreeCompare(resources);
		if (!useTreeCompare) {
			if (includeLocal) {
				compareWorkspaceWithRef(repository, resources[0],
						rightRev, page);
			} else {
				final IResource file = resources[0];
				final RepositoryMapping mapping = RepositoryMapping
						.getMapping(file);
				if (mapping == null) {
					Activator.error(NLS.bind(UIText.GitHistoryPage_errorLookingUpPath,
							file.getLocation(), repository), null);
					return;
				}
				final String gitPath = mapping.getRepoRelativePath(file);

				compareBetween(repository, gitPath, leftRev, rightRev, page);
			}
		} else {
			GitModelSynchronize.synchronize(resources, repository, leftRev,
					rightRev, includeLocal);
		}
	}

	private static boolean shouldUseTreeCompare(IResource[] resources) {
		if (resources.length == 1) {
			IResource resource = resources[0];
			if (resource instanceof IFile) {
				return !canDirectlyOpenInCompare((IFile) resource);
			} else {
				IPath location = resource.getLocation();
				if (location != null
						&& Files.isSymbolicLink(location.toFile().toPath())) {
					return false;
				}
			}
		}
		return true;
