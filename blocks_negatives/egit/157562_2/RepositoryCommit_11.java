	private void loadParents() throws IOException {
		try (RevWalk revWalk = new RevWalk(repository)) {
			for (RevCommit parent : commit.getParents())
				revWalk.parseBody(parent);
		}
	}

