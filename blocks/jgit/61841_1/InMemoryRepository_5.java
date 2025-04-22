			try {
				lock.writeLock().lock();
				ObjectId id = newRef.getObjectId();
				if (id != null) {
					try (RevWalk rw = new RevWalk(getRepository())) {
						rw.parseAny(id);
					}
