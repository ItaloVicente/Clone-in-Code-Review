		PersonIdent authorIdent;
		if (repo.getRepositoryState().equals(
				RepositoryState.CHERRY_PICKING_RESOLVED)) {
			try {
				ObjectId cherryPickHead = repo.readCherryPickHead();
				authorIdent = new RevWalk(repo).parseCommit(cherryPickHead)
						.getAuthorIdent();
			} catch (IOException e) {
				Activator
						.error(CoreText.CommitOperation_ParseCherryPickCommitFailed,
								e);
				throw new IllegalStateException(e);
			}
		} else {
			authorIdent = new PersonIdent(enteredAuthor, commitDate, timeZone);
		}

