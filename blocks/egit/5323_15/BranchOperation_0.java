
				List<String> pathsToHandle = new ArrayList<String>();
				pathsToHandle.addAll(co.getResult().getModifiedList());
				pathsToHandle.addAll(co.getResult().getRemovedList());
				IProject[] refreshProjects = ProjectUtil
						.getProjectsContaining(repository, pathsToHandle);
				ProjectUtil.refreshValidProjects(refreshProjects, delete,
