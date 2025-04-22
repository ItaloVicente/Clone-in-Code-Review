			try {
				if (closeProjectsAfterImport) {
					project.close(subMonitor.split(1));
				} else {
					buildIfRequested(project, subMonitor.split(1));

				}
			} catch (CoreException e) {
				return e.getStatus();
			}
