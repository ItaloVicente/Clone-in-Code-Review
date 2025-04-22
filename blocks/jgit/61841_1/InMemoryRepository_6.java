			try {
				lock.writeLock().lock();
				String name = oldRef.getName();
				Ref cur = refs.get(name);
				if (cur != null && eq(cur
					return refs.remove(name
				else
					return false;
			} finally {
				lock.writeLock().unlock();
			}
