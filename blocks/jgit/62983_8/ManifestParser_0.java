		removeNestedCopyfiles();
	}

	void removeNestedCopyfiles() {
		for (RepoProject proj : filteredProjects) {
			List<CopyFile> copyfiles = new ArrayList<>(proj.getCopyFiles());
			proj.clearCopyFiles();
			for (CopyFile copyfile : copyfiles) {
				if (!isNestedCopyfile(copyfile)) {
					proj.addCopyFile(copyfile);
				}
			}
		}
