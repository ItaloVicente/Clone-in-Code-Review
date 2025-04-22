	public Map<String, Ref> getAllRefs() {
		try {
			return getRefDatabase().getRefs(RefDatabase.ALL);
		} catch (IOException e) {
			return new HashMap<String, Ref>();
		}
	}
