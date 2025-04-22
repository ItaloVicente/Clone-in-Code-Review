		this.repository = repository;
		ObjectId objectId = repository.resolve(revstr);
		if (objectId != null)
			tree = new RevWalk(repository).parseTree(objectId);
		else
			tree = null;
		this.initialWorkingTreeIterator = workingTreeIterator;
