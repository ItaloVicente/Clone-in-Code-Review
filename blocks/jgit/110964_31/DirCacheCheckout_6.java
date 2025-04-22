			DirCacheEntry entryForUpdate = new DirCacheEntry(path
					DirCacheEntry.STAGE_0);
			entryForUpdate.setFileMode(mode);
			entryForUpdate.setObjectId(mId);

			if ((entry == null || !entry.isSkipWorkTree())
					&& skipSparse(path)) {
				entryForUpdate.setSkipWorkTree(true);
				removed.add(path);
			} else {
				updated.put(path
						new CheckoutMetadata(walk.getEolStreamType(CHECKOUT_OP)
								walk.getFilterCommand(
										Constants.ATTR_FILTER_TYPE_SMUDGE)));
			}
