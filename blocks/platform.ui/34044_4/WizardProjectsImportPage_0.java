				for (ProjectRecord selectedProject : selectedProjects) {
					if(selectedProject.hasConflicts) {
						projectsList.setChecked(selectedProject, false);
					} else {
						projectsList.setChecked(selectedProject, true);
					}
