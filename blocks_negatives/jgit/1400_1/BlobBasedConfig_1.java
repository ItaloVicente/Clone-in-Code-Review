		if (loader.isLarge()) {
			ObjectStream in = loader.openStream();
			try {
				byte[] buf = new byte[(int) in.getSize()];
				IO.readFully(in, buf, 0, buf.length);
				return buf;
			} finally {
				in.close();
			}
		}
		return loader.getCachedBytes();
