				ReflogReader reflogReader = repository.getReflogReader(Constants.HEAD);
				if (reflogReader != null) {
					List<ReflogEntry> lastEntry = reflogReader.getReverseEntries();
					for (ReflogEntry entry : lastEntry) {
						if (entry.getNewId().name().equals(commitId)) {
							CheckoutEntry checkoutEntry = entry.parseCheckout();
							if (checkoutEntry != null) {
								Ref ref = repository
										.findRef(checkoutEntry.getToBranch());
								if (ref != null) {
									ObjectId objectId = ref.getObjectId();
									if (objectId != null && objectId.getName()
											.equals(commitId)) {
										return checkoutEntry.getToBranch();
									}
									ref = repository.getRefDatabase().peel(ref);
