		removeNestedCopyfiles();
	}

	void removeNestedCopyfiles() {
		for (RepoProject proj : filteredProjects) {
			List<CopyFile> copyfiles = new ArrayList<>();
			copyfiles.addAll(proj.getCopyFiles());
			proj.clearCopyFiles();
			Iterator<CopyFile> iter = copyfiles.iterator();
			while (iter.hasNext()) {
				if (isNestedCopyfile(iter.next())) {
					iter.remove();
				}
			}
			proj.addCopyFiles(copyfiles);
		}
