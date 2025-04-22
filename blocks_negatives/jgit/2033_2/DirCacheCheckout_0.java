		if (ol == null)
			throw new MissingObjectException(entry.getObjectId(),
					Constants.TYPE_BLOB);

		byte[] bytes = ol.getCachedBytes();

