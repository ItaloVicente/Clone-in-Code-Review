
	private boolean shouldSplit() {
		return MAX_SIZE <= cnt && prefixLen + 2 < OBJECT_ID_STRING_LENGTH;
	}

	private InMemoryNoteBucket split() {
		FanoutBucket n = new FanoutBucket(prefixLen);
		for (int i = 0; i < cnt; i++)
			n.append(notes[i]);
		n.nonNotes = nonNotes;
		return n;
	}
