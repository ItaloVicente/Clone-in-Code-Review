	private ByteWindow getOrLoad(final PackFile pack
			throws IOException {
		final int slot = slot(pack
		final Entry e1 = table.get(slot);
		ByteWindow v = scan(e1
		if (v != null)
			return v;

		synchronized (lock(pack
			Entry e2 = table.get(slot);
			if (e2 != e1) {
				v = scan(e2
				if (v != null)
					return v;
			}

			v = load(pack
			final Ref ref = createRef(pack
			hit(ref);
			for (;;) {
				final Entry n = new Entry(clean(e2)
				if (table.compareAndSet(slot
					break;
				e2 = table.get(slot);
			}
		}

		if (evictLock.tryLock()) {
			try {
				gc();
				evict();
			} finally {
				evictLock.unlock();
			}
		}

		return v;
	}

	private ByteWindow scan(Entry n
		for (; n != null; n = n.next) {
			final Ref r = n.ref;
			if (r.pack == pack && r.position == position) {
				final ByteWindow v = r.get();
				if (v != null) {
					hit(r);
					return v;
				}
				n.kill();
				break;
			}
		}
		return null;
	}

	private void hit(final Ref r) {
		final long c = clock.get();
		clock.compareAndSet(c
		r.lastAccess = c;
	}

	private void evict() {
		while (isFull()) {
			int ptr = rng.nextInt(tableSize);
			Entry old = null;
			int slot = 0;
			for (int b = evictBatch - 1; b >= 0; b--
				if (tableSize <= ptr)
					ptr = 0;
				for (Entry e = table.get(ptr); e != null; e = e.next) {
					if (e.dead)
						continue;
					if (old == null || e.ref.lastAccess < old.ref.lastAccess) {
						old = e;
						slot = ptr;
					}
				}
			}
			if (old != null) {
				old.kill();
				gc();
				final Entry e1 = table.get(slot);
				table.compareAndSet(slot
			}
		}
	}

	private void removeAll() {
		for (int s = 0; s < tableSize; s++) {
			Entry e1;
			do {
				e1 = table.get(s);
				for (Entry e = e1; e != null; e = e.next)
					e.kill();
			} while (!table.compareAndSet(s
		}
		gc();
	}

	private void removeAll(final PackFile pack) {
		for (int s = 0; s < tableSize; s++) {
			final Entry e1 = table.get(s);
			boolean hasDead = false;
			for (Entry e = e1; e != null; e = e.next) {
				if (e.ref.pack == pack) {
					e.kill();
					hasDead = true;
				} else if (e.dead)
					hasDead = true;
			}
			if (hasDead)
				table.compareAndSet(s
		}
		gc();
	}

	@SuppressWarnings("unchecked")
	private void gc() {
		Ref r;
		while ((r = (Ref) queue.poll()) != null) {
			if (r.canClear()) {
				clear(r);

				boolean found = false;
				final int s = slot(r.pack
				final Entry e1 = table.get(s);
				for (Entry n = e1; n != null; n = n.next) {
					if (n.ref == r) {
						n.dead = true;
						found = true;
						break;
					}
				}
				if (found)
					table.compareAndSet(s
			}
		}
	}

	private int slot(final PackFile pack
		return (hash(pack.hash
	}

	private Lock lock(final PackFile pack
		return locks[(hash(pack.hash
	}

	private static Entry clean(Entry top) {
		while (top != null && top.dead) {
			top.ref.enqueue();
			top = top.next;
		}
		if (top == null)
			return null;
		final Entry n = clean(top.next);
		return n == top.next ? top : new Entry(n
	}

	private static class Entry {
		final Entry next;

		final Ref ref;

		volatile boolean dead;

		Entry(final Entry n
			next = n;
			ref = r;
		}

		final void kill() {
			dead = true;
			ref.enqueue();
		}
	}

	private static class Ref extends SoftReference<ByteWindow> {
		final PackFile pack;

		final long position;

