		}
		try (Git git = new Git(repo)) {
			for (RepoProject proj : filteredProjects) {
				addSubmodule(proj.getName()
						proj.getRevision()
						proj.getLinkFiles()
