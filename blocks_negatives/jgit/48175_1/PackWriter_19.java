	/**
	 * Prepare the list of objects to be written to the pack stream.
	 * <p>
	 * Basing on these 2 sets, another set of objects to put in a pack file is
	 * created: this set consists of all objects reachable (ancestors) from
	 * interesting objects, except uninteresting objects and their ancestors.
	 * This method uses class {@link ObjectWalk} extensively to find out that
	 * appropriate set of output objects and their optimal order in output pack.
	 * Order is consistent with general git in-pack rules: sort by object type,
	 * recency, path and delta-base first.
	 * </p>
	 *
	 * @param countingMonitor
	 *            progress during object enumeration.
	 * @param want
	 *            collection of objects to be marked as interesting (start
	 *            points of graph traversal).
	 * @param have
	 *            collection of objects to be marked as uninteresting (end
	 *            points of graph traversal).
	 * @throws IOException
	 *             when some I/O problem occur during reading objects.
	 * @deprecated to be removed in 2.0; use the Set version of this method.
	 */
	@Deprecated
	public void preparePack(ProgressMonitor countingMonitor,
			final Collection<? extends ObjectId> want,
			final Collection<? extends ObjectId> have) throws IOException {
		preparePack(countingMonitor, ensureSet(want), ensureSet(have));
	}

	/**
	 * Prepare the list of objects to be written to the pack stream.
	 * <p>
	 * Basing on these 2 sets, another set of objects to put in a pack file is
	 * created: this set consists of all objects reachable (ancestors) from
	 * interesting objects, except uninteresting objects and their ancestors.
	 * This method uses class {@link ObjectWalk} extensively to find out that
	 * appropriate set of output objects and their optimal order in output pack.
	 * Order is consistent with general git in-pack rules: sort by object type,
	 * recency, path and delta-base first.
	 * </p>
	 *
	 * @param countingMonitor
	 *            progress during object enumeration.
	 * @param walk
	 *            ObjectWalk to perform enumeration.
	 * @param interestingObjects
	 *            collection of objects to be marked as interesting (start
	 *            points of graph traversal).
	 * @param uninterestingObjects
	 *            collection of objects to be marked as uninteresting (end
	 *            points of graph traversal).
	 * @throws IOException
	 *             when some I/O problem occur during reading objects.
	 * @deprecated to be removed in 2.0; use the Set version of this method.
	 */
	@Deprecated
	public void preparePack(ProgressMonitor countingMonitor,
			ObjectWalk walk,
			final Collection<? extends ObjectId> interestingObjects,
			final Collection<? extends ObjectId> uninterestingObjects)
			throws IOException {
		preparePack(countingMonitor, walk,
				ensureSet(interestingObjects),
				ensureSet(uninterestingObjects));
	}

	@SuppressWarnings("unchecked")
	private static Set<ObjectId> ensureSet(Collection<? extends ObjectId> objs) {
		Set<ObjectId> set;
		if (objs instanceof Set<?>)
			set = (Set<ObjectId>) objs;
		else if (objs == null)
			set = Collections.emptySet();
		else
			set = new HashSet<ObjectId>(objs);
		return set;
	}

