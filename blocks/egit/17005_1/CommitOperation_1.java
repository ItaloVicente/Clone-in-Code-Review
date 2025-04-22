		PersonIdent authorIdent;
		if (repo.getRepositoryState().equals(
				RepositoryState.CHERRY_PICKING_RESOLVED)) {
			RevWalk rw = new RevWalk(repo);
			try {
				ObjectId cherryPickHead = repo.readCherryPickHead();
				authorIdent = rw.parseCommit(cherryPickHead)
						.getAuthorIdent();
			} catch (IOException e) {
				Activator
						.error(CoreText.CommitOperation_ParseCherryPickCommitFailed,
								e);
				throw new IllegalStateException(e);
			} finally {
				rw.release();
			}
		} else {
			authorIdent = new PersonIdent(enteredAuthor, commitDate, timeZone);
		}

