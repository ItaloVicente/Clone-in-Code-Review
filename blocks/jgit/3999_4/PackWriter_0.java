		preparePack(countingMonitor
				ensureSet(interestingObjects)
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

	public void preparePack(ProgressMonitor countingMonitor
			Set<? extends ObjectId> want
			Set<? extends ObjectId> have) throws IOException {
		ObjectWalk ow = new ObjectWalk(reader);
		preparePack(countingMonitor
	}

	public void preparePack(ProgressMonitor countingMonitor
			final ObjectWalk walk
			final Set<? extends ObjectId> interestingObjects
			final Set<? extends ObjectId> uninterestingObjects)
			throws IOException {
