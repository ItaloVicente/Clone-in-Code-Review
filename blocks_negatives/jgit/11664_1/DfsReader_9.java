	boolean wantReadAhead() {
		return wantReadAhead;
	}

	void startedReadAhead(List<ReadAheadTask.BlockFuture> blocks) {
		if (pendingReadAhead == null)
			pendingReadAhead = new LinkedList<ReadAheadTask.BlockFuture>();
		pendingReadAhead.addAll(blocks);
	}

	private void cancelReadAhead() {
		if (pendingReadAhead != null) {
			for (ReadAheadTask.BlockFuture f : pendingReadAhead)
				f.cancel(true);
			pendingReadAhead = null;
		}
		wantReadAhead = false;
	}

	private void waitForBlock(DfsPackKey key, long position)
			throws InterruptedIOException {
		Iterator<ReadAheadTask.BlockFuture> itr = pendingReadAhead.iterator();
		while (itr.hasNext()) {
			ReadAheadTask.BlockFuture f = itr.next();
			if (f.contains(key, position)) {
				try {
					f.get();
				} catch (InterruptedException e) {
					throw new InterruptedIOException();
				} catch (ExecutionException e) {
				}
				itr.remove();
				if (pendingReadAhead.isEmpty())
					pendingReadAhead = null;
				break;
			}
		}
	}

