
	@SuppressWarnings("null")
	private boolean filterLightweightTags(Ref ref) {
		ObjectId id = ref.getObjectId();
		try {
			return this.allTags || (id != null && (w.parseTag(id) != null));
		} catch (IOException e) {
			return false;
		}
	}
