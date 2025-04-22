		ObjectId root;
		try {
			root = dc.writeTree(inserter);
			inserter.flush();
		} finally {
			inserter.release();
		}
		return pool.lookupTree(root);
