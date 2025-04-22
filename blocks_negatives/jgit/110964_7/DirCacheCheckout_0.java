			updated.put(path, new CheckoutMetadata(
					walk.getEolStreamType(CHECKOUT_OP),
					walk.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE)));

			DirCacheEntry entry = new DirCacheEntry(path, DirCacheEntry.STAGE_0);
			entry.setObjectId(mId);
