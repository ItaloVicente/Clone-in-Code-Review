				List<ReflogEntry> lastEntry = safeReadReflog(repository,
						Constants.HEAD);
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
