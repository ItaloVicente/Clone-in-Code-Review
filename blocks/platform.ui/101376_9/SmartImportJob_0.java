			if (!openProjects) {
				this.report.keySet().forEach(project -> {
					try {
						project.close(monitor);
					} catch (CoreException e) {
						listener.errorHappened(project.getLocation(), e);
					}
				});
			}
