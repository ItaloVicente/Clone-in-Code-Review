		RevWalk rw = new RevWalk(getRepository());
		try {
			return update(rw);
		} finally {
			rw.release();
		}
