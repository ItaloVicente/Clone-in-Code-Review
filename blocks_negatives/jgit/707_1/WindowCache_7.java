	/**
	 * Clear every entry from the cache.
	 * <p>
	 * This is a last-ditch effort to clear out the cache, such as before it
	 * gets replaced by another cache that is configured differently. This
	 * method tries to force every cached entry through {@link #clear(Ref)} to
	 * ensure that resources are correctly accounted for and cleaned up by the
	 * subclass. A concurrent reader loading entries while this method is
	 * running may cause resource accounting failures.
	 */
	private void removeAll() {
		for (int s = 0; s < tableSize; s++) {
			Entry e1;
			do {
				e1 = table.get(s);
				for (Entry e = e1; e != null; e = e.next)
					e.kill();
			} while (!table.compareAndSet(s, e1, null));
		}
		gc();
	}

	/**
	 * Clear all entries related to a single file.
	 * <p>
	 * Typically this method is invoked during {@link PackFile#close()}, when we
	 * know the pack is never going to be useful to us again (for example, it no
	 * longer exists on disk). A concurrent reader loading an entry from this
	 * same pack may cause the pack to become stuck in the cache anyway.
	 *
	 * @param pack
	 *            the file to purge all entries of.
	 */
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
				table.compareAndSet(s, e1, clean(e1));
		}
		gc();
	}

