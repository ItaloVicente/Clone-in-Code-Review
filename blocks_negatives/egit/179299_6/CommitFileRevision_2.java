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
