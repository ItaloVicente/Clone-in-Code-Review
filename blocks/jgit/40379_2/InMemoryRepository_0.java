			ObjectId id = newRef.getObjectId();
			if (id != null) {
				RevWalk rw = new RevWalk(getRepository());
				try {
					rw.parseAny(id);
				} finally {
					rw.release();
				}
			}
