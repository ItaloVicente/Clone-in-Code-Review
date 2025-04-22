				GitSyncObjectCache obj = entry.getValue();
				if (members.containsKey(key)) {
					members.get(key).merge(obj, filterPaths);
				} else {
					members.put(key, obj);
				}
