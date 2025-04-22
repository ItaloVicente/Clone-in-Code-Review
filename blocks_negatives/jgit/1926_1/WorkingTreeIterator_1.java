	private void initializeDigestAndReadBuffer() {
		if (contentDigest != null)
			return;

		if (parent == null) {
			contentReadBuffer = new byte[BUFFER_SIZE];
			contentDigest = Constants.newMessageDigest();
		} else {
			final WorkingTreeIterator p = (WorkingTreeIterator) parent;
			p.initializeDigestAndReadBuffer();
			contentReadBuffer = p.contentReadBuffer;
			contentDigest = p.contentDigest;
		}
	}

