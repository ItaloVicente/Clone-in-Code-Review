		if (preDeleteProjectListener == null) {
			preDeleteProjectListener = new IResourceChangeListener() {

				public void resourceChanged(IResourceChangeEvent event) {
					IResource resource = event.getResource();
					if (resource instanceof IProject) {
						IProject project = (IProject) resource;
						if (RepositoryProvider.getProvider(project) instanceof GitProvider) {
							IResource dotGit = project.findMember(Constants.DOT_GIT);
							if (dotGit != null && dotGit.getType() == IResource.FOLDER)
								GitProjectData.reconfigureWindowCache();
						}
					}
				}
			};
			ResourcesPlugin.getWorkspace().addResourceChangeListener(preDeleteProjectListener, IResourceChangeEvent.PRE_DELETE);
		}
