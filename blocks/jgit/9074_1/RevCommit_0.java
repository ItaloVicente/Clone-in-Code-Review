		ObjectId sha1 = this.toObjectId();

		byte[] raw = commitCache.get(sha1);
		if (raw == null) {
			raw = walk.getCachedBytes(this);
			commitCache.put(sha1
		}

		parseCanonical(walk
