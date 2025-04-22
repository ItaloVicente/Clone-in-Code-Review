			if (cachedMembers != null) {
				Collection<GitSyncObjectCache> members = cachedMembers
						.members();
				if (members != null) {
					for (GitSyncObjectCache gitMember : members) {
						String name = gitMember.getName();
						IResource existing = existingMembers.get(name);
						if (existing != null) {
							allMembers.add(existing);
						}
						if (existing == null || (existing
								.getType() != IResource.FILE) != gitMember
										.getDiffEntry().isTree()) {
							IPath localPath = new Path(name);
							if (gitMember.getDiffEntry().isTree()) {
								allMembers.add(container.getFolder(localPath));
							} else {
								allMembers.add(container.getFile(localPath));
							}
						}
					}
				}
