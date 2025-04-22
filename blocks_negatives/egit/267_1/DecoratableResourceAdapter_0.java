		long tIndex = indexEntry.getLastModified();
		long tWorkspaceResource = resourceEntry.getLastModified();


		if (tIndex % 1000 == 0 || tWorkspaceResource % 1000 == 0) {
			return tIndex / 1000 == tWorkspaceResource / 1000;
		} else {
			return tIndex == tWorkspaceResource;
