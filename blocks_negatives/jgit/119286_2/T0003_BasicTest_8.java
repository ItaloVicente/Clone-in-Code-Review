				.build();
		assertEqualsPath(theDir, r.getDirectory());
		assertEqualsPath(theDir.getParentFile(), r.getWorkTree());
		assertEqualsPath(indexFile, r.getIndexFile());
		assertEqualsPath(objDir, r.getObjectDatabase().getDirectory());
		assertNotNull(r.open(ObjectId
				.fromString("6db9c2ebf75590eef973081736730a9ea169a0c4")));
		r.close();
