		if (repo.isBare()) {
			Project proj = new Project(url
			bareProjects.add(proj);
		} else {
			SubmoduleAddCommand add = git
				.submoduleAdd()
				.setPath(name)
				.setURI(url);
			if (monitor != null)
				add.setProgressMonitor(monitor);
			try {
				add.call();
			} catch (GitAPIException e) {
				throw new SAXException(e);
			}
