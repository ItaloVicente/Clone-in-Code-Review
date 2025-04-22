			DirCacheEntry entryForUpdate = new DirCacheEntry(path
					DirCacheEntry.STAGE_0);
			entryForUpdate.setFileMode(mode);
			entryForUpdate.setObjectId(mId);

			if ((entry == null || !entry.isSkipWorkTree())
					&& skipSparse(entryForUpdate)) {
				entryForUpdate.setSkipWorkTree(true);
				removed.add(path);
			} else {
				updated.put(path
						walk.getEolStreamType(CHECKOUT_OP)
						walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE)));
			}
