		tableSize = tableSize(cfg);
		final int lockCount = lockCount(cfg);
		if (tableSize < 1)
			throw new IllegalArgumentException("tSize must be >= 1");
		if (lockCount < 1)
			throw new IllegalArgumentException("lockCount must be >= 1");

		queue = new ReferenceQueue<ByteWindow>();
		clock = new AtomicLong(1);
		table = new AtomicReferenceArray<Entry>(tableSize);
		locks = new Lock[lockCount];
		for (int i = 0; i < locks.length; i++)
			locks[i] = new Lock();
		evictLock = new ReentrantLock();

		int eb = (int) (tableSize * .1);
		if (64 < eb)
			eb = 64;
		else if (eb < 4)
			eb = 4;
		if (tableSize < eb)
			eb = tableSize;
		evictBatch = eb;

