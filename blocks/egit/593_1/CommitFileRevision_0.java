
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((blobId == null) ? 0 : blobId.hashCode());
		result = prime * result + ((commit == null) ? 0 : commit.hashCode());
		result = prime * result + ((db == null) ? 0 : db.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommitFileRevision other = (CommitFileRevision) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (blobId == null) {
			if (other.blobId != null)
				return false;
		} else if (!blobId.equals(other.blobId))
			return false;
		if (commit == null) {
			if (other.commit != null)
				return false;
		} else if (!commit.equals(other.commit))
			return false;
		if (db == null) {
			if (other.db != null)
				return false;
		} else if (!db.equals(other.db))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
