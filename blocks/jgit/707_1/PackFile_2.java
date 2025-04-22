
			if (map.hasArray())
				return new ByteArrayWindow(this
			return new ByteBufferWindow(this
		} finally {
			putOpenHandle(handle);
