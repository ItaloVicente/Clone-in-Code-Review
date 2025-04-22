			selectedResources = new ArrayList();
			if (resource instanceof IWorkspaceRoot) {
				IProject[] projects = ((IWorkspaceRoot) resource).getProjects();
				for (IProject project : projects) {
					selectAppropriateFolderContents(project);
				}
			} else if (resource instanceof IFile) {
				IFile file = (IFile) resource;
				if (hasExportableExtension(file.getFullPath().toString())) {
