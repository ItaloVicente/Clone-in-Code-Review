	/**
	 * Finds next door tagged revision. Searches forwards (in descendants) or
	 * backwards (in ancestors)
	 *
	 * @param searchDescendant
	 *            if <code>false</code>, will search for tagged revision in
	 *            ancestors
	 * @param monitor
	 * @return {@link Ref} or <code>null</code> if no tag found
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private Ref getNextTag(boolean searchDescendant, IProgressMonitor monitor)
			throws IOException, InterruptedException {
		RevWalk revWalk = new RevWalk(db);

		Map<String, Ref> tagsMap = db.getTags();
		Ref tagRef = null;

		for (String tagName : tagsMap.keySet()) {
			if (monitor.isCanceled())
				throw new InterruptedException();
			RevCommit current = revWalk.parseCommit(commit);
			Ref ref = tagsMap.get(tagName);
			RevObject any = revWalk.peel(revWalk.parseAny(ref.getObjectId()));
			if (!(any instanceof RevCommit))
				continue;
			RevCommit newTag = (RevCommit) any;
			if (newTag.getId().equals(commit))
				continue;

			if (isMergedInto(revWalk, newTag, current, searchDescendant)) {
				if (tagRef != null) {
					RevCommit oldTag = revWalk
							.parseCommit(tagRef.getObjectId());

					if (isMergedInto(revWalk, oldTag, newTag, searchDescendant))
						tagRef = ref;
				} else
					tagRef = ref;
			}
		}
		return tagRef;
	}

	/**
	 * @param rw
	 * @param base
	 * @param tip
	 * @param swap
	 *            if <code>true</code>, base and tip arguments are swapped
	 * @return <code>true</code> if there is a path directly from tip to base
	 *         (and thus base is fully merged into tip); <code>false</code>
	 *         otherwise.
	 * @throws IOException
	 */
	private boolean isMergedInto(final RevWalk rw, final RevCommit base,
			final RevCommit tip, boolean swap) throws IOException {
		return !swap ? rw.isMergedInto(base, tip) : rw.isMergedInto(tip, base);
	}
