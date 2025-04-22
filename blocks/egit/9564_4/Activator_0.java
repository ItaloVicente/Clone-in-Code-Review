		if (egitChangeListener == null) {
			egitChangeListener = new IResourceChangeListener() {

				public void resourceChanged(IResourceChangeEvent event) {
					IResource project = event.getResource();
					if (project instanceof IProject)
						GitProjectData.reconfigureWindowCache();
				}
			};
			ResourcesPlugin.getWorkspace().addResourceChangeListener(egitChangeListener, IResourceChangeEvent.PRE_DELETE);
		}
