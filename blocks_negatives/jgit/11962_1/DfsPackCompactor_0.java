			List<ObjectIdWithOffset> want = new BlockList<ObjectIdWithOffset>();
			for (PackIndex.MutableEntry ent : src.getPackIndex(ctx)) {
				ObjectId id = ent.toObjectId();
				RevObject obj = rw.lookupOrNull(id);
				if (obj == null || !obj.has(added))
					want.add(new ObjectIdWithOffset(id, ent.getOffset()));
			}
