			return getTheirs(repository, walk);
		}
	}

	public static RevCommit getTheirs(Repository repository, RevWalk walk)
			throws IOException {
		String target;
		switch (repository.getRepositoryState()) {
		case MERGING:
			target = Constants.MERGE_HEAD;
			break;
		case CHERRY_PICKING:
			target = Constants.CHERRY_PICK_HEAD;
			break;
		case REBASING_INTERACTIVE:
			target = readFile(repository.getDirectory(),
					RebaseCommand.REBASE_MERGE + File.separatorChar
							+ RebaseCommand.STOPPED_SHA);
			break;
		case REVERTING:
			target = Constants.REVERT_HEAD;
			break;
		default:
			target = Constants.ORIG_HEAD;
			break;
		}
		ObjectId theirs = repository.resolve(target);
		if (theirs == null) {
			throw new IOException(MessageFormat.format(
					CoreText.ValidationUtils_CanNotResolveRefMessage, target));
		}
		return walk.parseCommit(theirs);
	}

	private static String readFile(File directory, String fileName)
			throws IOException {
		byte[] content = IO.readFully(new File(directory, fileName));
		int end = content.length;
		while (0 < end && content[end - 1] == '\n') {
			end--;
