		assertEquals(!removed
	}

	private Optional<Ref> getRef(Repository repo
			throws IOException {
		return getRef(repo.getRefDatabase().getRefs()
	}

	private Optional<Ref> getRef(List<Ref> refs
		return refs.stream().filter(r -> r.getName().equals(name)).findAny();
