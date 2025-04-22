			if (length < pos + size)
				size = (int) (length - pos);
			final byte[] buf = new byte[size];
			fd.seek(pos);
			fd.readFully(buf, 0, size);
			return new ByteArrayWindow(this, pos, buf);
		}
	}

	ByteWindow mmap(long pos, int size) throws IOException {
		synchronized (readLock) {
			if (length < pos + size)
				size = (int) (length - pos);

			MappedByteBuffer map;
			try {
				map = fd.getChannel().map(MapMode.READ_ONLY, pos, size);
			} catch (IOException ioe1) {
				System.gc();
				System.runFinalization();
				map = fd.getChannel().map(MapMode.READ_ONLY, pos, size);
			}

			if (map.hasArray())
				return new ByteArrayWindow(this, pos, map.array());
			return new ByteBufferWindow(this, pos, map);
