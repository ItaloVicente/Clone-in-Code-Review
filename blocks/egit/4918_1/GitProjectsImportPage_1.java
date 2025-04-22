	private class ProjectFolder {

		public ProjectFolder(String name) {
			super();
			this.name = name;
		}

		private String name;

		private List<ProjectRecord> projects = new ArrayList<ProjectRecord>();

		private Map<String, ProjectFolder> subfolders = new HashMap<String, ProjectFolder>();

		private void addProject(ProjectRecord project) {
			projects.add(project);
		}

		private void addProjectFolder(ProjectFolder folder) {
			subfolders.put(folder.getName(), folder);
		}

		public List<ProjectRecord> getProjects() {
			return projects;
		}

		public Collection<ProjectFolder> getSubfolders() {
			return subfolders.values();
		}

		public String getName() {
			return name;
		}

		public boolean hasFolder(String folder) {
			return getFolder(folder) != null;
		}

		public ProjectFolder getFolder(String folder) {
			return subfolders.get(folder);
		}

	}

