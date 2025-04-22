		if (preDeleteProjectListener == null) {
			preDeleteProjectListener = new IResourceChangeListener() {

				public void resourceChanged(IResourceChangeEvent event) {
					IResource project = event.getResource();
					if (project instanceof IProject)
						GitProjectData.reconfigureWindowCache();
				}
			};
			ResourcesPlugin.getWorkspace().addResourceChangeListener(preDeleteProjectListener, IResourceChangeEvent.PRE_DELETE);
		}
