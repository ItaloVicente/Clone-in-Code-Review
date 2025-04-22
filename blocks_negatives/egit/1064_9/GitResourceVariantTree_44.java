			if (tree.existsBlob(memberRelPath)) {
				TreeEntry entry = tree.findBlobMember(memberRelPath);
				ObjectLoader objLoader = repo.open(entry.getId(),
						Constants.OBJ_BLOB);
				store.setBytes(member, objLoader.getCachedBytes());
				membersSet.add(member);
			} else if (tree.existsTree(memberRelPath)) {
				membersSet.add(member);
			}
		}
		return membersSet;
