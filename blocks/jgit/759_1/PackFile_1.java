		synchronized (readLock) {
			if (length < pos + size)
				size = (int) (length - pos);
			final byte[] buf = new byte[size];
			fd.seek(pos);
			fd.readFully(buf
			return new ByteArrayWindow(this
		}
