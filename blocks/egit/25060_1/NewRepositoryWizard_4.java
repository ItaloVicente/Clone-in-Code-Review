
			if (!isBare && doAutoShare()) {
				IPath workTree = new Path(repoToCreate.getWorkTree()
						.getAbsolutePath());
				IProject[] projects = ProjectUtil
						.getProjectsUnderPath(workTree);
				if (projects.length == 0)
					return true;
				autoShareProjects(repoToCreate, projects);
			}
		} catch (GitAPIException e) {
