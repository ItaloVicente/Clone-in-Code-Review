		RevWalk rw = new RevWalk(this);
		try {
			return resolve(rw
		} finally {
			rw.release();
		}
	}

	private ObjectId resolve(final RevWalk rw
