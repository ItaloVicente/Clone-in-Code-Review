		boolean equalsRemoteId;
		ObjectId objRemoteId = objBlob.remoteId;
		if (objRemoteId != null)
			equalsRemoteId = objRemoteId.equals(remoteId);
		else
			equalsRemoteId = remoteId == null;
