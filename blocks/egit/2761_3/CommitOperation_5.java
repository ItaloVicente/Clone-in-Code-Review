	private void commitAll(final Date commitDate, final TimeZone timeZone,
			final PersonIdent authorIdent, final PersonIdent committerIdent)
			throws TeamException {
		for (Repository repo : repos) {
			Git git = new Git(repo);
			try {
				git.commit()
						.setAll(true)
						.setAuthor(
								new PersonIdent(authorIdent,
										commitDate, timeZone))
						.setCommitter(
								new PersonIdent(committerIdent,
										commitDate, timeZone))
						.setMessage(message).call();
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
			}
		}
	}

