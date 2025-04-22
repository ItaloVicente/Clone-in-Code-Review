		try {
			for (IResource child : container.members()) {
				if (child instanceof IFolder) {
					IProject project = NestedProjectManager.getProject((IFolder) child);
					if (project != null) {
						nestedProjects.add(project);
					}
				}
