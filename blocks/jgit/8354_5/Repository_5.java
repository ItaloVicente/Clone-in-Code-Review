	public void writeRevertHead(ObjectId head) throws IOException {
		List<ObjectId> heads = (head != null) ? Collections.singletonList(head)
				: null;
		writeHeadsFile(heads
	}

