		if (localName != null && localDb != null) {
			localUpdate = localDb.updateRef(localName);
			localUpdate.setForceUpdate(true);
			localUpdate.setRefLogMessage("push"
			localUpdate.setNewObjectId(newObjectId);
			trackingRefUpdate = new TrackingRefUpdate(
					true
					remoteName
					localName
					localUpdate.getOldObjectId() != null
						? localUpdate.getOldObjectId()
						: ObjectId.zeroId()
					newObjectId);
		} else
