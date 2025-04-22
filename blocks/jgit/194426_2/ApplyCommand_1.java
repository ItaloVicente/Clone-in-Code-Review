	ApplyCommand(Repository local) {
		super(local);
		this.inCore = false;
		if (local == null) {
			throw new NullPointerException(JGitText.get().repositoryIsRequired);
		}
		db = local;
		ObjectInserter inserter = local.newObjectInserter();
		reader = inserter.newReader();
		revWalk = new RevWalk(reader);
	}

	ApplyCommand(Repository remote
		super(null);
		db = null;
		inCore = true;
		revWalk = base;
		reader = base.getObjectReader();
