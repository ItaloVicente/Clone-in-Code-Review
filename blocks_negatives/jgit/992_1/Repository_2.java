	private Tree makeTree(final ObjectId id, final byte[] raw) throws IOException {
		Tree ret = new Tree(this, id, raw);
		return ret;
	}

	private Tag makeTag(final ObjectId id, final String refName, final byte[] raw) {
		Tag ret = new Tag(this, id, refName, raw);
		return ret;
	}

