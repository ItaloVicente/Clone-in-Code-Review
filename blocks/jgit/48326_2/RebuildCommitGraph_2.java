		try (final ObjectInserter oi = db.newObjectInserter()) {
			final ObjectId emptyTree = oi.insert(Constants.OBJ_TREE
					new byte[] {});
			final PersonIdent me = new PersonIdent("jgit rebuild-commitgraph"
			while (!queue.isEmpty()) {
				final ListIterator<ToRewrite> itr = queue
						.listIterator(queue.size());
				queue = new ArrayList<ToRewrite>();
				REWRITE: while (itr.hasPrevious()) {
					final ToRewrite t = itr.previous();
					final ObjectId[] newParents = new ObjectId[t.oldParents.length];
					for (int k = 0; k < t.oldParents.length; k++) {
						final ToRewrite p = toRewrite.get(t.oldParents[k]);
						if (p != null) {
							if (p.newId == null) {
								queue.add(t);
								continue REWRITE;
							} else {
								newParents[k] = p.newId;
							}
