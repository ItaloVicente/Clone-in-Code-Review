	private Map<String, Ref> getMostActiveBranches(final Repository repository,
			int maximumBranchCount) throws IOException {
		Map<String, Ref> localBranches = repository.getRefDatabase()
				.getRefs(Constants.R_HEADS);
		Map<String, Ref> activeRefs = new HashMap<>();

		ReflogReader reflogReader = repository.getReflogReader(Constants.HEAD);
		List<ReflogEntry> reflogEntries;
		if (reflogReader == null) {
			return Collections.emptyMap();
		}

		reflogEntries = reflogReader.getReverseEntries();

		for (ReflogEntry entry : reflogEntries) {
			CheckoutEntry checkout = entry.parseCheckout();
			if (checkout != null) {
				Ref ref = localBranches.get(checkout.getFromBranch());
				if (ref != null)
					if (activeRefs.size() < maximumBranchCount)
						activeRefs.put(checkout.getFromBranch(), ref);
				ref = localBranches.get(checkout.getToBranch());
				if (ref != null)
					if (activeRefs.size() < maximumBranchCount)
						activeRefs.put(checkout.getToBranch(), ref);
			}
		}

		return activeRefs;
	}

	private boolean isMultipleSelection(Repository[] repositories) {
		return repositories.length > 1;
	}

