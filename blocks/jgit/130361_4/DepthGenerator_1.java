
		for (ObjectId oid : w.getDeepenNots()) {
			RevCommit c;
			try {
				c = walk.parseCommit(oid);
			} catch (IncorrectObjectTypeException notCommit) {
				continue;
			}

			FIFORevQueue queue = new FIFORevQueue();
			queue.add(c);
			while ((c = queue.next()) != null) {
				if (c.has(DEEPEN_NOT)) {
					continue;
				}

				walk.parseHeaders(c);
				c.add(DEEPEN_NOT);
				for (RevCommit p : c.getParents()) {
					queue.add(p);
				}
			}
		}
