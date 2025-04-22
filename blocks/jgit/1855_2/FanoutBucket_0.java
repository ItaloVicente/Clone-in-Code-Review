	@Override
	int estimateSize(AnyObjectId noteOn
		if (LeafBucket.MAX_SIZE * 3 / 4 <= cnt)
			return 1 + LeafBucket.MAX_SIZE;


		MutableObjectId id = new MutableObjectId();
		id.fromObjectId(noteOn);

		int sz = 0;
		for (int cell = 0; cell < 256; cell++) {
			NoteBucket b = table[cell];
			if (b == null)
				continue;

			id.setByte(prefixLen >> 1
			sz += b.estimateSize(id
			if (LeafBucket.MAX_SIZE < sz)
				break;
		}
		return sz;
	}

