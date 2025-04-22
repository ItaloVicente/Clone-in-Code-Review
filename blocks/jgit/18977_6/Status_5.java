
	public Set<String> getUncommittedChanges() {
		Set<String> uncommittedChanges = new HashSet<String>();
		uncommittedChanges.addAll(diff.getAdded());
		uncommittedChanges.addAll(diff.getChanged());
		uncommittedChanges.addAll(diff.getRemoved());
		uncommittedChanges.addAll(diff.getMissing());
		uncommittedChanges.addAll(diff.getModified());
		uncommittedChanges.addAll(diff.getConflicting());
		return uncommittedChanges;
	}
