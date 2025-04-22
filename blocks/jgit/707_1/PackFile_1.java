			if (length < pos + size)
				size = (int) (length - pos);
			final byte[] buf = new byte[size];
			handle.fd.seek(pos);
			handle.fd.readFully(buf
			return new ByteArrayWindow(this
		} finally {
			putOpenHandle(handle);
