		RevWalk rw = new RevWalk(getRepository());
		RevObject obj = rw.parseAny(leaf.getObjectId());
		ObjectIdRef newLeaf;
		if (obj instanceof RevTag) {
			newLeaf = new ObjectIdRef.PeeledTag(leaf.getStorage(), leaf
					.getName(), leaf.getObjectId(), rw.peel(obj).copy());
		} else {
			newLeaf = new ObjectIdRef.PeeledNonTag(leaf.getStorage(), leaf
					.getName(), leaf.getObjectId());
		}
