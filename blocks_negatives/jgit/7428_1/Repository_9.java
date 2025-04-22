	public Map<String, Ref> getTags() {
		try {
			return getRefDatabase().getRefs(Constants.R_TAGS);
		} catch (IOException e) {
			return new HashMap<String, Ref>();
		}
	}
