	private void addUntrackedFiles(final Repository repository,
			final IProject project) throws CoreException, IOException {
		final GitIndex index = repository.getIndex();
		final Tree headTree = repository.mapTree(Constants.HEAD);
		project.accept(new IResourceVisitor() {

			public boolean visit(IResource resource) throws CoreException {
				if (Team.isIgnoredHint(resource))
					return false;
				if (resource.getType() == IResource.FILE) {

					String repoRelativePath = RepositoryMapping.getMapping(
							project).getRepoRelativePath(resource);
					try {
						TreeEntry headEntry = (headTree == null ? null
								: headTree.findBlobMember(repoRelativePath));
						if (headEntry == null) {
							Entry indexEntry = null;
							indexEntry = index.getEntry(repoRelativePath);

							if (indexEntry == null) {
								notTracked.add((IFile) resource);
								files.add((IFile) resource);
							}
						}
					} catch (IOException e) {
						throw new TeamException(
								UIText.CommitAction_InternalError, e);
					}
				}
				return true;
			}
		});

	}

