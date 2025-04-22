	private ObjectId getOriginalHead() throws IOException {
		try {
			return ObjectId.fromString(rebaseState.readFile(REBASE_HEAD));
		} catch (FileNotFoundException e) {
			try {
				return ObjectId
						.fromString(rebaseState.readFile(REBASE_HEAD_LEGACY));
			} catch (FileNotFoundException ex) {
				return repo.readOrigHead();
			}
		}
	}

