						IProject[] projects;
						if (projectsToShare == null)
							projects = getAddedProjects();
						else
							projects = projectsToShare;
						for (IProject prj : projects) {
