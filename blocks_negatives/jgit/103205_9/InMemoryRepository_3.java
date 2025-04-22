		protected boolean compareAndRemove(Ref oldRef) throws IOException {
			try {
				lock.writeLock().lock();
				String name = oldRef.getName();
				Ref cur = refs.get(name);
				if (cur != null && eq(cur, oldRef))
					return refs.remove(name, cur);
				else
					return false;
			} finally {
				lock.writeLock().unlock();
			}
		}

		private boolean eq(Ref a, Ref b) {
			if (!Objects.equals(a.getName(), b.getName()))
				return false;
			if (a.isSymbolic() != b.isSymbolic())
				return false;
			if (a.isSymbolic())
				return Objects.equals(a.getTarget().getName(), b.getTarget().getName());
			else
				return Objects.equals(a.getObjectId(), b.getObjectId());
