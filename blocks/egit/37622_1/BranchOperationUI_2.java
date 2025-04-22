		private boolean isAnyProjectInSourceContainers(
				ISourceContainer[] containers, Set<IProject> projects) {
			for (ISourceContainer container : containers) {
				if (container instanceof ProjectSourceContainer) {
					ProjectSourceContainer projectContainer = (ProjectSourceContainer) container;
					if (projects.contains(projectContainer.getProject()))
						return true;
				}
				try {
					boolean found = isAnyProjectInSourceContainers(
							container.getSourceContainers(), projects);
					if (found)
						return true;
				} catch (CoreException e) {
				}
			}
			return false;
		}
	};
