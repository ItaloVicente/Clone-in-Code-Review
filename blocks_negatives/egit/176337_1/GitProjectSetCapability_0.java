			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				@Override
				public void run(IProgressMonitor wsOpMonitor) throws CoreException {
					ProjectReferenceImporter importer = new ProjectReferenceImporter(referenceStrings);
					List<IProject> p = importer.run(wsOpMonitor);
					importedProjects.addAll(p);
				}
