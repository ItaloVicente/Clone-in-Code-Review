		RevWalk rw = new RevWalk(getRepository());
		RevObject obj = rw.parseAny(leaf.getObjectId());
		ObjectIdRef newLeaf;
		if (obj instanceof RevTag) {
			do {
				obj = rw.parseAny(((RevTag) obj).getObject());
			} while (obj instanceof RevTag);

			newLeaf = new ObjectIdRef.PeeledTag(leaf.getStorage(), leaf
					.getName(), leaf.getObjectId(), obj.copy());
		} else {
			newLeaf = new ObjectIdRef.PeeledNonTag(leaf.getStorage(), leaf
					.getName(), leaf.getObjectId());
		}
