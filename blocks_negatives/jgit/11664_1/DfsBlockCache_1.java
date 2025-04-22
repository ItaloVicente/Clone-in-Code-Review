	boolean readAhead(ReadableChannel rc, DfsPackKey key, int size, long pos,
			long len, DfsReader ctx) {
		if (!ctx.wantReadAhead() || readAheadLimit <= 0 || readAheadService == null)
			return false;

		int cap = readAheadLimit / size;
		long readAheadEnd = pos + readAheadLimit;
		List<ReadAheadTask.BlockFuture> blocks = new ArrayList<ReadAheadTask.BlockFuture>(cap);
		while (pos < readAheadEnd && pos < len) {
			long end = Math.min(pos + size, len);
			if (!contains(key, pos))
				blocks.add(new ReadAheadTask.BlockFuture(key, pos, end));
			pos = end;
		}
		if (blocks.isEmpty())
			return false;

		ReadAheadTask task = new ReadAheadTask(this, rc, blocks);
		ReadAheadTask.TaskFuture t = new ReadAheadTask.TaskFuture(task);
		for (ReadAheadTask.BlockFuture b : blocks)
			b.setTask(t);
		readAheadService.execute(t);
		ctx.startedReadAhead(blocks);
		return true;
	}

