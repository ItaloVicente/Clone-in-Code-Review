			if (tree.existsBlob(memberRelPath)) {
				TreeEntry entry = tree.findBlobMember(memberRelPath);
				ObjectLoader objLoader = repo.openBlob(entry.getId());
				store.setBytes(member, objLoader.getCachedBytes());
				membersSet.add(member);
			} else if (tree.existsTree(memberRelPath)) {
				membersSet.add(member);
			}
		}
		return membersSet;
