	private void commit(Repository repo, List<String> commitFileList, final Date commitDate,
			final TimeZone timeZone, final PersonIdent authorIdent,
			final PersonIdent committerIdent) throws TeamException {
		Git git = new Git(repo);
		try {
			CommitCommand commitCommand = git.commit();
			commitCommand
					.setAuthor(
							new PersonIdent(authorIdent,
									commitDate, timeZone))
					.setCommitter(
							new PersonIdent(committerIdent,
									commitDate, timeZone))
					.setAmend(amending)
					.setMessage(message)
					.setInsertChangeId(createChangeId);
			for(String path:commitFileList)
				commitCommand.setOnly(path);
			commitCommand.call();
		} catch (NoHeadException e) {
			throw new TeamException(e.getLocalizedMessage(), e);
		} catch (NoMessageException e) {
			throw new TeamException(e.getLocalizedMessage(), e);
		} catch (UnmergedPathException e) {
			throw new TeamException(e.getLocalizedMessage(), e);
		} catch (ConcurrentRefUpdateException e) {
			throw new TeamException(
					CoreText.MergeOperation_InternalError, e);
		} catch (JGitInternalException e) {
			throw new TeamException(
					CoreText.MergeOperation_InternalError, e);
		} catch (WrongRepositoryStateException e) {
			throw new TeamException(e.getLocalizedMessage(), e);
