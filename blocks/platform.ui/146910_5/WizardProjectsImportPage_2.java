			try {
				buildIfRequested(project, mon);
			} catch (CoreException e) {
				return e.getStatus();
			}
			if (closeProjectsAfterImport) {
				try {
					project.close(subMonitor.split(1));
				} catch (CoreException e) {
					return e.getStatus();
				}
			}
