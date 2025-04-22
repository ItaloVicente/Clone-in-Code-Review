	private void fillValues(final Repository repository) throws IOException {
		gitDir.setText(repository.getDirectory().getAbsolutePath());
		branch.setText(repository.getBranch());
		workDir.setText(repository.getWorkTree().getAbsolutePath());

		state.setText(repository.getRepositoryState().getDescription());
	}

	private RepositoryCommit getCommit(Repository repository, ObjectId objectId) {
		RevWalk walk = new RevWalk(repository);
		try {
			RevCommit commit = walk.parseCommit(objectId);
			for (RevCommit parent : commit.getParents())
				walk.parseBody(parent);
			return new RepositoryCommit(repository, commit);
		} catch (IOException e) {
			Activator.showError(NLS.bind(
					UIText.GitProjectPropertyPage_UnableToGetCommit,
					objectId.name()), e);
		} finally {
			walk.release();
		}
		return null;
	}
