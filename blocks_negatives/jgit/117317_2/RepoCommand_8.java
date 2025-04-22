		if (repo.isBare()) {
			RepoProject proj = new RepoProject(url, path, revision, null, groups, recommendShallow);
			proj.addCopyFiles(copyfiles);
			proj.addLinkFiles(linkfiles);
			bareProjects.add(proj);
		} else {
			if (!linkfiles.isEmpty()) {
				throw new UnsupportedOperationException(
						JGitText.get().nonBareLinkFilesNotSupported);
			}
