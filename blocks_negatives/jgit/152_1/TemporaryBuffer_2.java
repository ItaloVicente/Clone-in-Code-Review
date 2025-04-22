			diskOut.write(tmp, 0, n);
	}

	private Block last() {
		return blocks.get(blocks.size() - 1);
	}

	private boolean reachedInCoreLimit() throws IOException {
		if (blocks.size() * Block.SZ < inCoreLimit)
			return false;

		onDiskFile = File.createTempFile("jgit_", ".buffer");
		diskOut = new FileOutputStream(onDiskFile);

		final Block last = blocks.remove(blocks.size() - 1);
		for (final Block b : blocks)
			diskOut.write(b.buffer, 0, b.count);
		blocks = null;

		diskOut = new BufferedOutputStream(diskOut, Block.SZ);
		diskOut.write(last.buffer, 0, last.count);
		return true;
	}

	public void close() throws IOException {
		if (diskOut != null) {
			try {
				diskOut.close();
			} finally {
				diskOut = null;
			}
		}
