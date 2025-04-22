				SubMonitor subMonitor = SubMonitor.convert(monitor, countClosedProjects());
				subMonitor.setTaskName(getOperationMessage());
				for (IResource resource : resources) {
					if (!(resource instanceof IProject)) {
						continue;
					}

					IProject project = (IProject) resource;
					if (!project.exists() || project.isOpen()) {
						continue;
