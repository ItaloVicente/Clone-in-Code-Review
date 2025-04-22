	static byte[] asByteArray(RevObject obj
		if (loader.isLarge()) {
			ObjectStream in = loader.openStream();
			try {
				long sz = in.getSize();
				if (Integer.MAX_VALUE <= sz){
					if (obj != null)
						throw new LargeObjectException(obj.copy());
					throw new LargeObjectException();
				}
				byte[] buf = new byte[(int) sz];
				IO.readFully(in
				return buf;
			} finally {
				in.close();
			}
		}
		return loader.getCachedBytes();
	}

