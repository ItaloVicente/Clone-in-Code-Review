	public List<Ref> execute() {
		try {
			return new ArrayList<>(repo.getRefDatabase().getRefsByPrefix("refs/heads/"));
		} catch (java.io.IOException e) {
			throw new RuntimeException(e);
		}
	}
