
			if (!openProjects) {
				try {
					project.close(subMonitor.split(1));
				} catch (CoreException e) {
					return e.getStatus();
				}
			}

