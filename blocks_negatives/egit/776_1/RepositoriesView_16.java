				for (IProject prj : projectsToDelete) {
					prj.delete(false, false, actMonitor);
				}
				for (Repository repo : repository)
					removeDir(repo.getDirectory());
				scheduleRefresh();
