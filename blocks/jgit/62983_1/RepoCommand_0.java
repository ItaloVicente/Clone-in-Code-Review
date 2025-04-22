
	private boolean isNestedCopyfile(
			CopyFile copyfile
		for (RepoProject proj : sortedProjects) {
			if (proj.getPath().compareTo(copyfile.dest) > 0)
				return false;
			if (proj.isAncestorOf(copyfile.dest))
				return true;
		}
		return false;
	}
