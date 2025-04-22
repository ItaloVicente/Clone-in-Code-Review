	}

	private void makeInitialCommit(Git git) throws GitAPIException {
		git.commit().setMessage("initial commit").call();
	}

	private void makeSomeChangesAndStageThem(Git git) throws IOException
			GitAPIException {
