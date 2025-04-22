	void merge(GitSyncObjectCache value) {
		if (value.members != null) {
			if (members == null)
				members = new HashMap<String, GitSyncObjectCache>();
			else
				for (Entry<String, GitSyncObjectCache> entry : members
						.entrySet())
					if (!value.members.containsKey(entry.getKey()))
						entry.getValue().diffEntry.changeType = ChangeType.IN_SYNC;

			for (Entry<String, GitSyncObjectCache> entry : value.members
					.entrySet()) {
				String key = entry.getKey();
				if (members.containsKey(key))
					members.get(key).merge(entry.getValue());
				else
					members.put(key, entry.getValue());
			}
		} else if (members != null)
			for (GitSyncObjectCache obj : members.values())
				obj.diffEntry.changeType = ChangeType.IN_SYNC;
