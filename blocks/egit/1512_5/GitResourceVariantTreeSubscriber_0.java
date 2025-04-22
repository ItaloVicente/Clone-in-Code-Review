		GitSynchronizeData gsd = gsds.getData(local.getProject());

		SyncInfo info;
		if (gsd.shouldIncludeLocal())
			info = new SyncInfo(local, base, remote, getResourceComparator());
		else
			info = new GitSyncInfo(local, base, remote, getResourceComparator(), gsd);

