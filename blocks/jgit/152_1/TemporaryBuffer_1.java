		for (final Block b : blocks) {
			os.write(b.buffer
			pm.update(b.count / 1024);
		}
	}

	public void reset() {
		if (overflow != null) {
			destroy();
		}
		blocks = new ArrayList<Block>(inCoreLimit / Block.SZ);
		blocks.add(new Block());
	}

	protected abstract OutputStream overflow() throws IOException;

	private Block last() {
		return blocks.get(blocks.size() - 1);
	}

	private boolean reachedInCoreLimit() throws IOException {
		if (blocks.size() * Block.SZ < inCoreLimit)
			return false;

		overflow = overflow();

		final Block last = blocks.remove(blocks.size() - 1);
		for (final Block b : blocks)
			overflow.write(b.buffer
		blocks = null;

		overflow = new BufferedOutputStream(overflow
		overflow.write(last.buffer
		return true;
	}

	public void close() throws IOException {
		if (overflow != null) {
			try {
				overflow.close();
			} finally {
				overflow = null;
			}
		}
	}

	public void destroy() {
		blocks = null;

		if (overflow != null) {
			try {
				overflow.close();
			} catch (IOException err) {
			} finally {
				overflow = null;
			}
		}
	}

	public static class LocalFile extends TemporaryBuffer {
		private File onDiskFile;

		public LocalFile() {
			this(DEFAULT_IN_CORE_LIMIT);
		}

		public LocalFile(final int inCoreLimit) {
			super(inCoreLimit);
		}

		protected OutputStream overflow() throws IOException {
			onDiskFile = File.createTempFile("jgit_"
			return new FileOutputStream(onDiskFile);
		}

		public long length() {
			if (onDiskFile == null) {
				return super.length();
