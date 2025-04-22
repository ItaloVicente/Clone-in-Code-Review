		Repository r;

		r = new FileKey(db.getDirectory(), db.getFS()).open(true);
		assertNotNull(r);
		assertEqualsFile(db.getDirectory(), r.getDirectory());
		r.close();
