	private void setExistingTagFromText(String tagName) {
		RevWalk revWalk = null;
		try {
			ObjectId tagObjectId = repo.resolve(Constants.R_TAGS + tagName);
			if (tagObjectId != null) {
				revWalk = new RevWalk(repo);
				RevObject tagObject = revWalk.parseAny(tagObjectId);
				setExistingTag(tagObject);
				return;
			}
		} catch (IOException e) {
		} catch (RevisionSyntaxException e) {
		} finally {
			if (revWalk != null)
				revWalk.release();
