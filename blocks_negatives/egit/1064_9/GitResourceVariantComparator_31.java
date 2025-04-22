		if (!resourceBase.exists() || !resourceRemote.exists()) {
			return false;
		}

		if (base.isContainer()) {
			if (remote.isContainer()) {
				return resourceBase.getFullPath().equals(
						resourceRemote.getFullPath());
			}
			return false;
		} else if (remote.isContainer()) {
			return false;
		}

		GitBlobResourceVariant baseBlob = (GitBlobResourceVariant) base;
		GitBlobResourceVariant remoteBlob = (GitBlobResourceVariant) remote;
		return baseBlob.getId().equals(remoteBlob.getId());
