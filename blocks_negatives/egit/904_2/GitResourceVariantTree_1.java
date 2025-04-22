				if (entry != null) {
					ObjectLoader objLoader = repo.openBlob(entry.getId());
					store.setBytes(member, objLoader.getCachedBytes());
					membersSet.add(member);
				}
			} else if (member.getType() == IResource.FOLDER ) {
				try {
					IResource[] resources = ((IContainer) member).members();
					membersSet.addAll(getAllMembers(repo, tree, resources));
				} catch (CoreException e) {
					throw new TeamException(e.getStatus());
				}
