

			DirCacheEntry entryForUpdate = new DirCacheEntry(path
					DirCacheEntry.STAGE_0);
			entryForUpdate.setFileMode(mode);
			entryForUpdate.setObjectId(mId);

			if ((entry == null || !entry.isSkipWorkTree())
					&& skipSparse(path)
					|| (entry != null && isSparseCheckout
							&& !sparseCheckoutFileExists
							&& entry.isSkipWorkTree())) {
				entryForUpdate.setSkipWorkTree(true);
				removed.add(path);
				builder.add(entryForUpdate);
			} else {
				if (force || entry != null) {
					updated.put(path
							walk.getEolStreamType(CHECKOUT_OP)
							walk.getFilterCommand(
									Constants.ATTR_FILTER_TYPE_SMUDGE)));
					builder.add(entryForUpdate);
				}
			}
