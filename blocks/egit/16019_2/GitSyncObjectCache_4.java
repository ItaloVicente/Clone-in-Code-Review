		} else if (members != null) {
			for (GitSyncObjectCache obj : members.values()) {
				String entryPath = obj.getDiffEntry().getPath();
				if (filterPaths.contains(entryPath))
					obj.getDiffEntry().changeType = ChangeType.IN_SYNC;
			}
		} else {
			diffEntry = other.diffEntry;
		}
