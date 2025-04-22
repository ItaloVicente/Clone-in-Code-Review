			try {
				ReflogReader reflogReader = repository.getReflogReader(Constants.HEAD);
				if (reflogReader != null) {
					List<ReflogEntry> lastEntry = reflogReader.getReverseEntries();
					for (ReflogEntry entry : lastEntry) {
						if (entry.getNewId().name().equals(commitId)) {
							CheckoutEntry checkoutEntry = entry.parseCheckout();
							if (checkoutEntry != null) {
								Ref ref = repository.getRef(checkoutEntry.getToBranch());
								if (ref != null)
									ref = repository.peel(ref);
								if (ref != null && ref.getPeeledObjectId().getName().equals(commitId))
									return checkoutEntry.getToBranch();
							}
						}
					}
				}
			} catch (IOException e) {
			}

