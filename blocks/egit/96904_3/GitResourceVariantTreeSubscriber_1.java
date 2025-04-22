			IContainer container = (IContainer) res;
			if (container.exists()) {
				for (IResource member : container.members()) {
					allMembers.put(member.getName(), member);
				}
			}
			if (cachedMembers != null) {
				Collection<GitSyncObjectCache> members = cachedMembers
						.members();
				if (members != null) {
					for (GitSyncObjectCache gitMember : members) {
						String name = gitMember.getName();
						IResource existing = allMembers.get(name);
						if (existing == null || (existing
								.getType() != IResource.FILE) != gitMember
										.getDiffEntry().isTree()) {
							IPath localPath = new Path(name);
							if (gitMember.getDiffEntry().isTree()) {
								allMembers.put(name,
										container.getFolder(localPath));
							} else {
								allMembers.put(name,
										container.getFile(localPath));
							}
						}
					}
				}
