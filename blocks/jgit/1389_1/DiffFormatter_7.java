	private String format(AbbreviatedObjectId id) {
		if (id.isComplete() && db != null) {
			ObjectReader reader = db.newObjectReader();
			try {
				id = reader.abbreviate(id.toObjectId()
			} catch (IOException cannotAbbreviate) {
			} finally {
				reader.release();
			}
		}
		return id.name();
