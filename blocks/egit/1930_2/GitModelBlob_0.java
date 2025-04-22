	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof GitModelBlob) {
			GitModelBlob objCommit = (GitModelBlob) obj;

			return objCommit.baseId.equals(baseId)
					&& objCommit.remoteId.equals(remoteId);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int result = baseId.hashCode();
		if (remoteId != null)
			result ^= remoteId.hashCode();

		return result;
	}

