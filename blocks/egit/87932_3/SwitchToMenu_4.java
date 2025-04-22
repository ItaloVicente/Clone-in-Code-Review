				localBranches.addAll(local.keySet());

				ReflogReader reflogReader = repository
						.getReflogReader(Constants.HEAD);
				List<ReflogEntry> reflogEntries;
				if (reflogReader == null) {
					reflogEntries = Collections.emptyList();
				} else {
					reflogEntries = reflogReader.getReverseEntries();
				}
				for (ReflogEntry entry : reflogEntries) {
					CheckoutEntry checkout = entry.parseCheckout();
					if (checkout != null) {
						Ref ref = local.get(checkout.getFromBranch());
						if (ref != null)
							refs.put(checkout.getFromBranch(), ref);
						ref = local.get(checkout.getToBranch());
						if (ref != null)
							refs.put(checkout.getToBranch(), ref);
					}
				}
				for (Entry<String, Ref> refEntry : refs.entrySet()) {
					if (sortedRefs.size() < MAX_NUM_MENU_ENTRIES) {
						sortedRefs.add(refEntry.getKey());
					}
