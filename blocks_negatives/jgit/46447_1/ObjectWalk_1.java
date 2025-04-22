	@Override
	public void markUninteresting(RevCommit c) throws MissingObjectException,
			IncorrectObjectTypeException, IOException {
		super.markUninteresting(c);
		try {
			markTreeUninteresting(c.getTree());
		} catch (MissingObjectException e) {
		}
	}

	@Override
