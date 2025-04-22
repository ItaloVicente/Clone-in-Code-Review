
				List<String> pathsToHandle = new ArrayList<String>();
				pathsToHandle.addAll(co.getResult().getModifiedList());
				pathsToHandle.addAll(co.getResult().getRemovedList());
				IProject[] validProjects = ProjectUtil
						.getProjectsContaining(repository, pathsToHandle);
