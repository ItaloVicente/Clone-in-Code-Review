	public void setWorkingSets(Set<IWorkingSet> workingSets) {
		if (workingSets != null) {
			this.workingSets = workingSets.toArray(new IWorkingSet[workingSets.size()]);
		} else {
			this.workingSets = new IWorkingSet[0];
		}
	}

