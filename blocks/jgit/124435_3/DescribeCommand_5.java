
	@SuppressWarnings("null")
	private boolean filterLightweightTags(Ref ref) {
		ObjectId id = ref.getObjectId();
		try {
			return this.useTags || (id != null && (w.parseTag(id) != null));
		} catch (IOException e) {
			return false;
		}
	}
