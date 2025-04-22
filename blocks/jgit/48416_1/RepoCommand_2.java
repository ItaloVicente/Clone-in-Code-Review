
			filteredProjects.addAll(projects);
			removeNotInGroup();
			removeOverlaps();
		}

		public List<Project> getProjects() {
			return projects;
		}

		public List<Project> getFilteredProjects() {
			return filteredProjects;
