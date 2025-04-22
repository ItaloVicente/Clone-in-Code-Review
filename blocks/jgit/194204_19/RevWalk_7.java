	public void updateCommit(RevCommit newCommit) {
		RevCommit oldCommit = (RevCommit) this.objects.get(newCommit.getId());
		if (oldCommit != null
				&& oldCommit.getParentCount() == newCommit.getParentCount()) {
			Set<RevCommit> newParents = Set.of(newCommit.getParents());
			Set<RevCommit> oldParents = Set.of(oldCommit.getParents());
			if (!newParents.containsAll(oldParents)) {
				this.objects.add(newCommit);
			}
		}
	}

