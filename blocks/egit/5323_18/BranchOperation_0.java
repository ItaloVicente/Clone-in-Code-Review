
				List<String> pathsToHandle = new ArrayList<String>();
				pathsToHandle.addAll(co.getResult().getModifiedList());
				pathsToHandle.addAll(co.getResult().getRemovedList());
				pathsToHandle.addAll(co.getResult().getConflictList());
				IProject[] refreshProjects = ProjectUtil
						.getProjectsContaining(repository, pathsToHandle);
				ProjectUtil.refreshValidProjects(refreshProjects, delete,
