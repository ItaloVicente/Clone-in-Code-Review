		if (project.exists()) {
			TestUtils.deleteProject(project);
		} else {
			File f = new File(location);
			if (f.exists())
				FileUtils.delete(f, FileUtils.RECURSIVE | FileUtils.RETRY);
