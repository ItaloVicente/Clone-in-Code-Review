
	private Ref determineHead() throws IOException {
		Ref h = repo.exactRef(Constants.HEAD);
		if (h != null && h.isSymbolic()) {
			return h;
		}
		return null;
	}
