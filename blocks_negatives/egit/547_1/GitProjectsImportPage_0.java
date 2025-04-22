	class ProjectRecord {
		File projectSystemFile;

		String projectName;

		Object parent;

		int level;

		IProjectDescription description;

		/**
		 * Create a record for a project based on the info in the file.
		 *
		 * @param file
		 */
		ProjectRecord(File file) {
			projectSystemFile = file;
			setProjectName();
		}

		/**
		 * @param parent
		 *            The parent folder of the .project file
		 * @param level
		 *            The number of levels deep in the provider the file is
		 */
		ProjectRecord(Object parent, int level) {
			this.parent = parent;
			this.level = level;
			setProjectName();
		}

		/**
		 * Set the name of the project based on the projectFile.
		 */
		private void setProjectName() {
			try {
				if (projectName == null) {
					IPath path = new Path(projectSystemFile.getPath());
					if (isDefaultLocation(path)) {
						projectName = path.segment(path.segmentCount() - 2);
						description = ResourcesPlugin.getWorkspace()
								.newProjectDescription(projectName);
					} else {
						description = ResourcesPlugin.getWorkspace()
								.loadProjectDescription(path);
						projectName = description.getName();
					}

				}
			} catch (CoreException e) {
			}
		}

		/**
		 * Returns whether the given project description file path is in the
		 * default location for a project
		 *
		 * @param path
		 *            The path to examine
		 * @return Whether the given path is the default location for a project
		 */
		private boolean isDefaultLocation(IPath path) {
			if (path.segmentCount() < 2)
				return false;
			return path.removeLastSegments(2).toFile().equals(
					Platform.getLocation().toFile());
		}

		/**
		 * Get the name of the project
		 *
		 * @return String
		 */
		public String getProjectName() {
			return projectName;
		}

		/**
		 * Gets the label to be used when rendering this project record in the
		 * UI.
		 *
		 * @return String the label
		 * @since 3.4
		 */
		public String getProjectLabel() {
			if (description == null)
				return projectName;

			String path = projectSystemFile == null ? structureProvider
					.getLabel(parent) : projectSystemFile.getParent();

			return NLS.bind(UIText.WizardProjectsImportPage_projectLabel,
					projectName, path);
		}
	}

