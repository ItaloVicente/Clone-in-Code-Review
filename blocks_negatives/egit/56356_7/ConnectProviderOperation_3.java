		try {
			for (Entry<IProject, File> entry : projects.entrySet()) {
				connectProject(entry, ms, monitor);
			}
		} finally {
			monitor.done();
