	public void updateCommit(RevCommit commit) {
		RevCommit object = (RevCommit) this.objects.get(commit.getId());
		if (object != null && commit.getParents() != object.getParents()) {
			object.parents = commit.getParents();
			return;
		}
	}

