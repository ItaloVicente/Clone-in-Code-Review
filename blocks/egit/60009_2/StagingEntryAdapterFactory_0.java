		if (adaptableObject != null) {
			StagingEntry entry = (StagingEntry) adaptableObject;
			if (adapterType == IResource.class) {
				return entry.getFile();
			} else if (adapterType == IPath.class) {
				return entry.getLocation();
			} else if (adapterType == Repository.class) {
				return entry.getRepository();
			}
		}
		return null;
