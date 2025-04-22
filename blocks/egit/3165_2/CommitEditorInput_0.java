	public int hashCode() {
		return commit.getRevCommit().hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		else if (obj instanceof CommitEditorInput) {
			RepositoryCommit inputCommit = ((CommitEditorInput) obj).commit;
			return commit.getRepository().equals(inputCommit.getRepository())
					&& commit.getRevCommit().equals(inputCommit.getRevCommit());
		} else
			return false;
	}

	public String toString() {
		return getName();
	}

