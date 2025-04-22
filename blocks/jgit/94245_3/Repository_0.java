	public RevCommit parseCommit(AnyObjectId id) throws IncorrectObjectTypeException
			IOException
		if (id instanceof RevCommit && ((RevCommit) id).getRawBuffer() != null) {
			return (RevCommit) id;
		}
		try (RevWalk walk = new RevWalk(this)) {
			return walk.parseCommit(id);
		}
	}

