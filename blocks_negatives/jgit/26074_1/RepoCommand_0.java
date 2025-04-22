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
