				if (!isNestedReferencefile(copyfile)) {
					proj.addCopyFile(copyfile);
				}
			}
		}
	}

	private void removeNestedCopyAndLinkfiles() {
		for (RepoProject proj : filteredProjects) {
			List<CopyFile> copyfiles = new ArrayList<>(proj.getCopyFiles());
			proj.clearCopyFiles();
			for (CopyFile copyfile : copyfiles) {
				if (!isNestedReferencefile(copyfile)) {
