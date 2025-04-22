
		Set<GitSyncObjectCache> gitCachedMembers = new HashSet<GitSyncObjectCache>();
		String path = stripWorkDir(repo.getWorkTree(), res.getLocation().toFile());
		GitSyncObjectCache cachedMembers = repoCache.get(path);
		if (cachedMembers != null) {
			Collection<GitSyncObjectCache> members = cachedMembers.members();
			if (members != null)
				gitCachedMembers.addAll(members);
		}
