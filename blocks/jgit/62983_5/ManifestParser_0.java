		removeNestedCopyfiles();
	}

	void removeNestedCopyfiles() {
		for (RepoProject proj : filteredProjects) {
			List<CopyFile> copyfiles = new ArrayList<CopyFile>();
			copyfiles.addAll(proj.getCopyFiles());
			proj.clearCopyFiles();
			for (CopyFile copyfile : copyfiles)
				if (!isNestedCopyfile(copyfile))
					proj.addCopyFile(copyfile);
		}
