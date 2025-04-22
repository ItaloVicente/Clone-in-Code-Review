	@Override
	public RevCommit next() throws MissingObjectException
			IncorrectObjectTypeException
		PlotCommit<?> pc = (PlotCommit) super.next();
		if (pc != null)
			pc.refs = getTags(pc);
		return pc;
	}

	private Ref[] getTags(final AnyObjectId commitId) {
