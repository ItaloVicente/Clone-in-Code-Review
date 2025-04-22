
	private boolean isNestedCopyfile(CopyFile copyfile) {
		if (copyfile.dest.indexOf('/') == -1) {
			return false;
		}
		for (RepoProject proj : filteredProjects) {
			if (proj.getPath().compareTo(copyfile.dest) > 0) {
				return false;
			}
			if (proj.isAncestorOf(copyfile.dest)) {
				return true;
			}
		}
		return false;
	}
