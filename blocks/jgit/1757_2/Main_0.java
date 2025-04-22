	protected Repository openGitDir(String gitdir) throws IOException {
				.findGitDir();
		if (rb.getGitDir() == null)
			throw new Die(CLIText.get().cantFindGitDirectory);
		return rb.build();
	}

