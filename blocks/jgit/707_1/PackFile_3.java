	private OpenFile getOpenHandle() throws IOException {
		lastAccess = PackFileHandleCache.tick();
		AvailableFileNode r
		do {
			r = fds.get();
			if (r == null)
				return newOpenHandle();
			n = r.next;
		} while (!fds.compareAndSet(r
		return r.handle;
