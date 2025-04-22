		final ObjectId treeId;
		final ObjectInserter oi = db.newObjectInserter();
		try {
			final ObjectId blobId = oi.insert(Constants.OBJ_BLOB
					"and this is the data in me\n".getBytes(Constants.CHARSET));
			TreeFormatter fmt = new TreeFormatter();
			fmt.append("i-am-a-file"
			treeId = oi.insert(fmt);
			oi.flush();
		} finally {
			oi.release();
		}
		assertEquals(ObjectId
				.fromString("00b1f73724f493096d1ffa0b0f1f1482dbb8c936")
