			if (isDefaultLocation(path)) {
				projectName = path.segment(path.segmentCount() - 2);
				description = ResourcesPlugin.getWorkspace()
						.newProjectDescription(projectName);
			} else {
				description = ResourcesPlugin.getWorkspace()
						.loadProjectDescription(path);
				projectName = description.getName();
			}
