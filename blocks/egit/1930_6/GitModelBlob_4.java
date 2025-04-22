	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof GitModelBlob) {
			GitModelBlob objBlob = (GitModelBlob) obj;

			boolean equalsRemoteId;
			ObjectId objRemoteId = objBlob.remoteId;
			if (objRemoteId != null)
				equalsRemoteId = objRemoteId.equals(remoteId);
			else
				equalsRemoteId = baseCommit == null;

			return objBlob.baseId.equals(baseId) && equalsRemoteId;
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

