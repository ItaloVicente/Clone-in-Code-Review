		try {
			if (project.exists()) {
				TestUtils.deleteProject(project);
			} else {
				File f = new File(location);
				if (f.exists()) {
					FileUtils.delete(f, FileUtils.RECURSIVE | FileUtils.RETRY);
				}
			}
		} catch (CoreException | IOException e) {
			System.err.println(e.toString());
			TestUtils.listDirectory(new File(location), true);
			throw e;
