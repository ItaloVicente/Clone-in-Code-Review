		} else if (members != null) {
			for (GitSyncObjectCache obj : members.values()) {
				String entryPath = obj.getDiffEntry().getPath();
				if (containsPathOrParent(filterPaths, entryPath))
					obj.getDiffEntry().changeType = ChangeType.IN_SYNC;
			}
		} else {
			diffEntry = other.diffEntry;
		}
