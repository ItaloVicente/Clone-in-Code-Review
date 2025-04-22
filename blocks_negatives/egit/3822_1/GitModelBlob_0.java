		if (obj instanceof GitModelBlob && !(obj instanceof GitModelCacheFile)
				&& !(obj instanceof GitModelWorkingFile)) {
			GitModelBlob objBlob = (GitModelBlob) obj;

			boolean equalsRemoteId;
			ObjectId objRemoteId = objBlob.remoteId;
			if (objRemoteId != null)
				equalsRemoteId = objRemoteId.equals(remoteId);
			else
				equalsRemoteId = remoteId == null;

			return objBlob.baseId.equals(baseId) && equalsRemoteId
					&& objBlob.location.equals(location);
		}
