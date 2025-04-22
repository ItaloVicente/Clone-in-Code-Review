			return UIIcons.CHANGESET.createImage();
	}

	private boolean isCommit(final String ref) {
		return !ref.startsWith(Constants.R_REFS);
	}

	private RevCommit getLatestCommit(String branch) {
		ObjectId resolved;
		try {
			resolved = repo.resolve(branch);
		} catch (IOException e) {
			return null;
		}
		if (resolved == null)
			return null;
		RevWalk walk = new RevWalk(repo);
		walk.setRetainBody(true);
		try {
			return walk.parseCommit(resolved);
		} catch (IOException ignored) {
			return null;
		} finally {
			walk.release();
		}
