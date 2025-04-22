	@Override
	public int hashCode() {
		final int prime = 31;
		return prime * super.hashCode()
				+ ((commit == null) ? 0 : commit.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommitBlobStorage other = (CommitBlobStorage) obj;
		if (commit == null) {
			if (other.commit != null)
				return false;
		} else if (!commit.equals(other.commit))
			return false;
		return true;
	}


