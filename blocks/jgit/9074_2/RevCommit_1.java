		byte[] raw;

		synchronized (commitCache) {
			raw = commitCache.get(this);
		}
		if (raw == null) {
			raw = walk.getCachedBytes(this);
			ObjectId sha1 = this.toObjectId();
			synchronized (commitCache) {
				commitCache.put(sha1
			}
		}

		parseCanonical(walk
