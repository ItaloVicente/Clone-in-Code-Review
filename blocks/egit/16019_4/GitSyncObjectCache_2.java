			for (Entry<String, GitSyncObjectCache> entry : members.entrySet()) {
				String key = entry.getKey();
				if (!other.members.containsKey(key)) {
					GitSyncObjectCache obj = entry.getValue();
					String entryPath = obj.getDiffEntry().getPath();
					if (containsPathOrParent(filterPaths, entryPath))
						obj.getDiffEntry().changeType = ChangeType.IN_SYNC;
				}
			}

			for (Entry<String, GitSyncObjectCache> entry : other.members
