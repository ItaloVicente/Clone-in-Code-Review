	@Override
	public void markUninteresting(RevCommit c) throws MissingObjectException
			IncorrectObjectTypeException
		super.markUninteresting(c);
		markTreeUninteresting(c.getTree());
	}

