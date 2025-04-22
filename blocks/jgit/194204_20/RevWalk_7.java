	public void updateCommit(RevCommit newCommit) {
		RevCommit oldCommit = (RevCommit) this.objects.get(newCommit.getId());
		if (oldCommit != null) {
			this.objects.add(newCommit);
		}
	}

