
	private class ProjectAndRepo {
		private IProject project;
		private String repo;

		public ProjectAndRepo(IProject project, String repo) {
			this.project = project;
			this.repo = repo;
		}

		public IProject getProject() {
			return project;
		}

		public String getRepo() {
			return repo;
		}
	}
