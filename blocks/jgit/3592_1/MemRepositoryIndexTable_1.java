
	public void remove(RepositoryName name
			throws DhtException
		boolean ok = table.compareAndSet(
				name.asBytes()
				colId.name()
				key.asBytes()
				null);
		if (!ok)
			throw new DhtException(MessageFormat.format(
					DhtText.get().repositoryAlreadyExists
	}
