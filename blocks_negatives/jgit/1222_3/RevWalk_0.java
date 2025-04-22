		if (r == null) {
			final ObjectLoader ldr = reader.open(id);
			final int type = ldr.getType();
			switch (type) {
			case Constants.OBJ_COMMIT: {
				final RevCommit c = createCommit(id);
				c.parseCanonical(this, ldr.getCachedBytes());
				r = c;
				break;
			}
			case Constants.OBJ_TREE: {
				r = new RevTree(id);
				r.flags |= PARSED;
				break;
			}
			case Constants.OBJ_BLOB: {
				r = new RevBlob(id);
				r.flags |= PARSED;
				break;
