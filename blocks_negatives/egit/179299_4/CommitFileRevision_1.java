		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((blobId == null) ? 0 : blobId.hashCode());
		result = prime * result + ((commit == null) ? 0 : commit.hashCode());
		result = prime * result + ((db == null) ? 0 : db.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
