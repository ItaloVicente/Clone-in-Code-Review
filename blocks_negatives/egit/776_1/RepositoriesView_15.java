		for (Repository repo : repository) {
			File workDir = repo.getWorkDir();
			final IPath wdPath = new Path(workDir.getAbsolutePath());
			for (IProject prj : ResourcesPlugin.getWorkspace().getRoot()
					.getProjects()) {
				if (monitor.isCanceled())
					return;
				if (wdPath.isPrefixOf(prj.getLocation())) {
					projectsToDelete.add(prj);
