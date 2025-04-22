	@Override
	protected void reset(int retainFlags) {
		headInitialized = false;
		head = null;
		super.reset(retainFlags);
	}

	@Override
	public RevCommit next() throws MissingObjectException,
			IncorrectObjectTypeException, IOException {
		if (!headInitialized) {
			head = determineHead();
			headInitialized = true;
		}
		return super.next();
	}

	public Ref getHead() throws IOException {
		if (!headInitialized) {
			head = determineHead();
			headInitialized = true;
		}
		return head;
	}

