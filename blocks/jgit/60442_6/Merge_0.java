	private Ref getOldHead() throws IOException {
		Ref oldHead = db.getRef(Constants.HEAD);
		if (oldHead == null) {
			throw die(CLIText.get().onBranchToBeBorn);
		}
		return oldHead;
	}

