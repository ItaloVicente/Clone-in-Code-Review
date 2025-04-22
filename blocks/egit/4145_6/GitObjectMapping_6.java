		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (!object.isContainer()) {
			IFile file = root.getFileForLocation(object.getLocation());

			return new IProject[] {file.getProject()};
		} else {
			if (object instanceof GitModelTree) {
				IContainer container = root.getContainerForLocation(object.getLocation());

				return new IProject[] {container.getProject()};
			} else if (object instanceof HasProjects)
				return ((HasProjects) object).getProjects();
			else
				return null;
		}
