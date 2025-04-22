		if (new File(srcGitdir, Constants.DOT_GIT).isDirectory())
			srcGitdir = new File(srcGitdir, Constants.DOT_GIT);
		db = new Repository(srcGitdir);
		if (!db.getObjectsDirectory().isDirectory())
			throw die(MessageFormat.format(CLIText.get().notAGitRepository, srcGitdir.getPath()));
		rp = new org.eclipse.jgit.transport.UploadPack(db);
