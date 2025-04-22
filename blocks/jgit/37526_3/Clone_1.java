			branch = Constants.HEAD;

		CloneCommand command = Git.cloneRepository();
		command.setURI(sourceUri).setRemote(remoteName)
				.setNoCheckout(noCheckout).setBranch(branch);

		if (localName != null && localName.length() > 0)
			dirPath = localName;
		if (gitdir != null && gitdir.length() > 0)
			dirPath = gitdir;
		command.setDirectory(new File(dirPath));
