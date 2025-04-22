			record.setProjectDescription(workspace
					.newProjectDescription(projectName));
			IPath locationPath = new Path(record.getProjectSystemFile()
					.getAbsolutePath());

			if (Platform.getLocation().isPrefixOf(locationPath))
				record.getProjectDescription().setLocation(null);
			else
				record.getProjectDescription().setLocation(locationPath);
		} else {
			record.getProjectDescription().setName(projectName);
