					.addConfiguredRepository(gitDir);

			if (!isBare && doAutoShare()) {
				IPath workTree = new Path(repository.getWorkTree()
						.getAbsolutePath());
				IProject[] projects = ProjectUtil
						.getProjectsUnderPath(workTree);
				if (projects.length == 0)
					return true;
				autoShareProjects(repository, projects);
			}
		} catch (GitAPIException e) {
			org.eclipse.egit.ui.Activator.handleError(e.getMessage(), e, false);
