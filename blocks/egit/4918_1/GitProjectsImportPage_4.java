				Object element = event.getElement();
				if(element instanceof ProjectFolder) {
					ProjectFolder projectFolder = (ProjectFolder) element;
					List<ProjectRecord> projects = projectFolder.getProjects();
					for (ProjectRecord projectRecord : projects) {
						checkProject(projectRecord);
					}
