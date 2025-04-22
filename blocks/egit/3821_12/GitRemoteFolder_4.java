		monitor.beginTask("Fetching members of " + getPath(), cachedData.membersCount()); //$NON-NLS-1$
		try {
			for (GitSyncObjectCache member : members) {
				DiffEntry diffEntry = member.getDiffEntry();
				String memberPath = diffEntry.getOldPath();

				if (DiffEntry.DEV_NULL.equals(memberPath))
					continue;

				GitRemoteResource obj;
				ObjectId id = diffEntry.getOldId().toObjectId();
				if (FileMode.TREE == diffEntry.getOldMode())
					obj = new GitRemoteFolder(repo, member, getCommitId(), id,
							memberPath);
				else
					obj = new GitRemoteFile(repo, getCommitId(), id, memberPath);

				result.add(obj);
				monitor.worked(1);
