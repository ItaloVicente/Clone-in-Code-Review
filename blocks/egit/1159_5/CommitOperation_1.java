				final PersonIdent authorIdent = new PersonIdent(author);
				final PersonIdent committerIdent = new PersonIdent(committer);
				if (mergeResolve) {
					for (Repository repo : repos) {
						Git git = new Git(repo);
						try {
							git.commit().setAll(true).setAuthor(authorIdent)
									.setCommitter(committerIdent)
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

				else if (amending || filesToCommit != null
