			preDeleteProjectListener = event -> {
				IResource resource = event.getResource();
				if (resource instanceof IProject) {
					IProject project = (IProject) resource;
					if (project.isAccessible()) {
						if (ResourceUtil.isSharedWithGit(project)) {
							IResource dotGit1 = project
									.findMember(Constants.DOT_GIT);
							if (dotGit1 != null && dotGit1
									.getType() == IResource.FOLDER) {
								GitProjectData.reconfigureWindowCache();
