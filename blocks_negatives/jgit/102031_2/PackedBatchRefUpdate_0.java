			LockFile packedRefsLock = new LockFile(refdb.packedRefsFile);
			try {
				packedRefsLock.lock();
				refdb.commitPackedRefs(packedRefsLock, newRefs, oldPackedList);
			} finally {
				packedRefsLock.unlock();
