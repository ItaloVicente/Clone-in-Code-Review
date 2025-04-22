
		@Override
		public PackFile getPack() {
			return pack;
		}

		@Override
		public long getPosition() {
			return position;
		}

		@Override
		public int getSize() {
			return size;
		}

		@Override
		public long getLastAccess() {
			return lastAccess;
		}

		@Override
		public void setLastAccess(long time) {
			this.lastAccess = time;
		}

		@Override
		public boolean kill() {
			return enqueue();
		}

		@Override
		public boolean isStrongRef() {
			return false;
		}
	}

	private static class StrongRef implements PageRef<ByteWindow> {
		private ByteWindow referent;

		private final PackFile pack;

		private final long position;

		private final int size;

		private long lastAccess;

		private CleanupQueue queue;

		protected StrongRef(final PackFile pack
				final ByteWindow v
			this.pack = pack;
			this.position = position;
			this.referent = v;
			this.size = v.size();
			this.queue = queue;
		}

		@Override
		public PackFile getPack() {
			return pack;
		}

		@Override
		public long getPosition() {
			return position;
		}

		@Override
		public int getSize() {
			return size;
		}

		@Override
		public long getLastAccess() {
			return lastAccess;
		}

		@Override
		public void setLastAccess(long time) {
			this.lastAccess = time;
		}

		@Override
		public ByteWindow get() {
			return referent;
		}

		@Override
		public boolean kill() {
			if (referent == null) {
				return false;
			}
			referent = null;
			return queue.enqueue(this);
		}

		@Override
		public boolean isStrongRef() {
			return true;
		}
	}

	private static interface CleanupQueue {
		boolean enqueue(PageRef<ByteWindow> r);
		void gc();
	}

	private static class SoftCleanupQueue extends ReferenceQueue<ByteWindow>
			implements CleanupQueue {
		private final WindowCache wc;

		SoftCleanupQueue(WindowCache cache) {
			this.wc = cache;
		}

		@Override
		public boolean enqueue(PageRef<ByteWindow> r) {
			return false;
		}

		@Override
		public void gc() {
			SoftRef r;
			while ((r = (SoftRef) poll()) != null) {
				wc.clear(r);

				final int s = wc.slot(r.getPack()
				final Entry e1 = wc.table.get(s);
				for (Entry n = e1; n != null; n = n.next) {
					if (n.ref == r) {
						n.dead = true;
						wc.table.compareAndSet(s
						break;
					}
				}
			}
		}
	}

	private static class StrongCleanupQueue implements CleanupQueue {
		private final WindowCache wc;

		private final ConcurrentLinkedQueue<PageRef<ByteWindow>> queue = new ConcurrentLinkedQueue<>();

		StrongCleanupQueue(WindowCache wc) {
			this.wc = wc;
		}

		@Override
		public boolean enqueue(PageRef<ByteWindow> r) {
			if (queue.contains(r)) {
				return false;
			}
			return queue.add(r);
		}

		@Override
		public void gc() {
			PageRef<ByteWindow> r;
			while ((r = queue.poll()) != null) {
				wc.clear(r);

				final int s = wc.slot(r.getPack()
				final Entry e1 = wc.table.get(s);
				for (Entry n = e1; n != null; n = n.next) {
					if (n.ref == r) {
						n.dead = true;
						wc.table.compareAndSet(s
						break;
					}
				}
			}
		}
