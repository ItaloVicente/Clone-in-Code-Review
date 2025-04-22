		Repository repository = getRepository();
		FS fs = repository.getFS();
		if (fs == null) {
			fs = FS.DETECTED;
		}
		ProcessResult result = fs.runHookIfPresent(repository
				getParameters()
				getStdinArgs());
