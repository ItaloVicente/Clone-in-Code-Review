		if (pack.beginWindowCache())
			openFiles.incrementAndGet();
		try {
			if (mmap)
				return pack.mmap(offset, windowSize);
			return pack.read(offset, windowSize);
		} catch (IOException e) {
			close(pack);
			throw e;
		} catch (RuntimeException e) {
			close(pack);
			throw e;
		} catch (Error e) {
			close(pack);
			throw e;
		}
