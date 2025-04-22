		CloneCommand command = Git.cloneRepository();
		command.setURI(sourceUri).setRemote(remoteName)
				.setNoCheckout(noCheckout).setBranch(branch);
		if (localName != null && localName.length() > 0)
			command.setDirectory(new File(localName));
		if (gitdir != null && gitdir.length() > 0)
			command.setDirectory(new File(gitdir));
