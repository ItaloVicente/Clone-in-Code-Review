		assert (bareProjects != null);
		RepoProject proj = new RepoProject(url, path, revision, null, groups,
				recommendShallow);
		proj.addCopyFiles(copyfiles);
		proj.addLinkFiles(linkfiles);
		bareProjects.add(proj);
