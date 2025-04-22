
			List<ReflogEntry> reflogEntries = new ReflogReader(
					mapping.getRepository(), Constants.HEAD)
					.getReverseEntries();
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
			}

