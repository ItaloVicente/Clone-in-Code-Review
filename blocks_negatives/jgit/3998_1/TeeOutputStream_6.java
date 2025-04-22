	/**
	 * @throws JGitInternalException
	 *             upon internal failure
	 * @return the tags available
	 */
	public List<RevTag> call() throws JGitInternalException {
		checkCallable();
		Map<String, Ref> refList;
		List<RevTag> tags = new ArrayList<RevTag>();
		RevWalk revWalk = new RevWalk(repo);
		try {
			refList = repo.getRefDatabase().getRefs(Constants.R_TAGS);
			for (Ref ref : refList.values()) {
				RevTag tag = revWalk.parseTag(ref.getObjectId());
				tags.add(tag);
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} finally {
			revWalk.release();
		}
		Collections.sort(tags, new Comparator<RevTag>() {
			public int compare(RevTag o1, RevTag o2) {
				return o1.getTagName().compareTo(o2.getTagName());
			}
		});
		setCallable(false);
		return tags;
