			try {
				lock.readLock().lock();
				for (Ref ref : refs.values()) {
					if (ref.isSymbolic())
						sym.add(ref);
					ids.add(ref);
				}
			} finally {
				lock.readLock().unlock();
