	private RebaseResult stop(RevCommit commitToPick) throws IOException {
		StringBuilder sb = new StringBuilder(100);
		sb.append("GIT_AUTHOR_NAME='");
		sb.append(commitToPick.getAuthorIdent().getName());
		sb.append("'\n");
		sb.append("GIT_AUTHOR_EMAIL='");
		sb.append(commitToPick.getAuthorIdent().getEmailAddress());
		sb.append("'\n");
		sb.append("GIT_AUTHOR_DATE='");
		sb.append(commitToPick.getAuthorIdent().getWhen());
		sb.append("'\n");
		createFile(rebaseDir
		createFile(rebaseDir
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DiffFormatter df = new DiffFormatter(bos);
		df.setRepository(repo);
		df.format(commitToPick.getParent(0)
		createFile(rebaseDir
		return new RebaseResult(commitToPick);
	}

