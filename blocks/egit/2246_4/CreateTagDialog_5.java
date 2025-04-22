	private List<Object> getRevTags() throws IOException {
		List<Object> result = new ArrayList<Object>();
		Collection<Ref> refs = repo.getRefDatabase().getRefs(Constants.R_TAGS)
				.values();
		for (Ref ref : refs) {
			RevObject any;
