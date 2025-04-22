		RevWalk rw = new RevWalk(getRepository());
		try {
			return delete(rw);
		} finally {
			rw.release();
		}
