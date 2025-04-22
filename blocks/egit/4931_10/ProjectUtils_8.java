		if (project.exists()) {
			if (open && !project.isOpen()) {
				IPath location = project.getLocation();
				if (location != null
						&& location.toFile().equals(
								record.getProjectSystemFile()))
					project.open(monitor);
			}
