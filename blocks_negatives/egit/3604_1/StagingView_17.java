	private String getCherryPickOriginalAuthor(Repository mergeRepository) {
		try {
			ObjectId cherryPickHead = mergeRepository.readCherryPickHead();
			PersonIdent author = new RevWalk(mergeRepository).parseCommit(cherryPickHead).getAuthorIdent();
		} catch (IOException e) {
			Activator.handleError(UIText.CommitAction_errorRetrievingCommit, e,
					true);
			throw new IllegalStateException(e);
		}
