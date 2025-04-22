		protected void save() throws CoreException {
			element.saveDocument(true, null);
			refreshIndexDiff();
		}

		private void refreshIndexDiff() {
			IResource resource = element.getResource();
			if (resource != null && HiddenResources.INSTANCE
					.isHiddenProject(resource.getProject())) {
				String gitPath = null;
				Repository repository = null;
				URI uri = resource.getLocationURI();
				if (EFS.SCHEME_FILE.equals(uri.getScheme())) {
					IPath location = new Path(uri.getSchemeSpecificPart());
					repository = ResourceUtil.getRepository(location);
					if (repository != null) {
						location = ResourceUtil.getRepositoryRelativePath(
								location, repository);
						if (location != null) {
							gitPath = location.toPortableString();
						}
					}
				} else {
					repository = HiddenResources.INSTANCE.getRepository(uri);
					if (repository != null) {
						gitPath = HiddenResources.INSTANCE.getGitPath(uri);
					}
				}
				if (gitPath != null && repository != null) {
					IndexDiffCacheEntry indexDiffCacheForRepository = IndexDiffCache
							.getInstance().getIndexDiffCacheEntry(repository);
					if (indexDiffCacheForRepository != null) {
						indexDiffCacheForRepository.refreshFiles(
								Collections.singletonList(gitPath));
					}
				}
			}
		}

