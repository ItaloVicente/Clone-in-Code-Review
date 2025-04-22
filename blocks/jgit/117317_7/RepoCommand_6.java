		for (CopyFile copyfile : copyfiles) {
			copyfile.copy();
			git.add().addFilepattern(copyfile.dest).call();
		}
	}

	private void addSubmoduleBare(String url
			List<CopyFile> copyfiles
			Set<String> groups
		assert (repo.isBare());
		assert (bareProjects != null);
		RepoProject proj = new RepoProject(url
				recommendShallow);
		proj.addCopyFiles(copyfiles);
		proj.addLinkFiles(linkfiles);
		bareProjects.add(proj);
