
			private void checkProject(ProjectRecord project) {
				if (isProjectInWorkspace(project.getProjectName())) {
					projectsList.setChecked(project, false);
				}
			}
