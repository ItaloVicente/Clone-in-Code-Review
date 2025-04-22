
			unassignedProjects = new HashSet<>(Arrays.asList(ResourcesPlugin.getWorkspace().getRoot().getProjects()));
			for (Entry<IAdaptable, IAdaptable> tree : parents.entrySet()) {
				unassignedProjects.remove(tree.getKey().getAdapter(IProject.class));
			}
		}

		public Set<IProject> getUnassignedProjects() {
			return unassignedProjects;
