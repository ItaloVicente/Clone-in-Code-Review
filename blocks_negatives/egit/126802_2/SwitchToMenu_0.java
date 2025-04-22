			String currentBranch = repository.getFullBranch();
			Map<String, Ref> localBranches = repository.getRefDatabase().getRefs(
					Constants.R_HEADS);
			TreeMap<String, Ref> sortedRefs = new TreeMap<>(
					CommonUtils.STRING_ASCENDING_COMPARATOR);

			ReflogReader reflogReader = repository.getReflogReader(
					Constants.HEAD);
			List<ReflogEntry> reflogEntries;
			if (reflogReader == null) {
				reflogEntries = Collections.emptyList();
			} else {
				reflogEntries = reflogReader.getReverseEntries();
			}
			for (ReflogEntry entry : reflogEntries) {
				CheckoutEntry checkout = entry.parseCheckout();
				if (checkout != null) {
					Ref ref = localBranches.get(checkout.getFromBranch());
					if (ref != null)
						if (sortedRefs.size() < MAX_NUM_MENU_ENTRIES)
							sortedRefs.put(checkout.getFromBranch(), ref);
					ref = localBranches.get(checkout.getToBranch());
					if (ref != null)
						if (sortedRefs.size() < MAX_NUM_MENU_ENTRIES)
							sortedRefs.put(checkout.getToBranch(), ref);
				}
