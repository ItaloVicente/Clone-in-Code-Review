	void hash(ObjectLoader obj) throws MissingObjectException
		if (obj.isLarge()) {
			ObjectStream in = obj.openStream();
			try {
				setFileSize(in.getSize());
				hash(in
			} finally {
				in.close();
			}
		} else {
			byte[] raw = obj.getCachedBytes();
			setFileSize(raw.length);
			hash(raw
		}
