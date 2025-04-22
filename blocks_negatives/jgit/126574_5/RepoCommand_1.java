	private void addSubmoduleBare(String url, String path, String revision,
			List<CopyFile> copyfiles, List<LinkFile> linkfiles,
			Set<String> groups, String recommendShallow) {
		assert (repo.isBare());
		assert (bareProjects != null);
		RepoProject proj = new RepoProject(url, path, revision, null, groups,
				recommendShallow);
		proj.addCopyFiles(copyfiles);
		proj.addLinkFiles(linkfiles);
		bareProjects.add(proj);
