		final PackOutputStream out = new PackOutputStream(
			writeMonitor,
			isIndexDisabled()
				? packStream
				: new CheckedOutputStream(packStream, crc32),
			this);
