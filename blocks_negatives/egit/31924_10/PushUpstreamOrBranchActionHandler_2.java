		final Repository repository = getRepository();
		if (repository == null)
			return false;

		Ref head = getHeadIfSymbolic(repository);
		if (head == null)
			return false;

		return true;
	}
