	@Override
	Collection<? extends CachedPack> getCachedPacks() throws IOException {
		CachedPackList list = cachedPacks.get();
		if (list == null || list.snapshot.isModified(cachedPacksFile))
			list = scanCachedPacks(list);

		Collection<CachedPack> result = list.getCachedPacks();
		boolean resultIsCopy = false;

		for (AlternateHandle h : myAlternates()) {
			Collection<CachedPack> altPacks = h.getCachedPacks();
			if (altPacks.isEmpty())
				continue;

			if (result.isEmpty()) {
				result = altPacks;
				continue;
			}

			if (!resultIsCopy) {
				result = new ArrayList<CachedPack>(result);
				resultIsCopy = true;
			}
			result.addAll(altPacks);
		}
		return result;
	}

	private CachedPackList scanCachedPacks(CachedPackList old)
			throws IOException {
		FileSnapshot s = FileSnapshot.save(cachedPacksFile);
		byte[] buf;
		try {
			buf = IO.readFully(cachedPacksFile);
		} catch (FileNotFoundException e) {
			buf = new byte[0];
		}

		if (old != null && old.snapshot.equals(s)
				&& Arrays.equals(old.raw
			old.snapshot.setClean(s);
			return old;
		}

		ArrayList<LocalCachedPack> list = new ArrayList<LocalCachedPack>(4);
		Set<ObjectId> tips = new HashSet<ObjectId>();
		int ptr = 0;
		while (ptr < buf.length) {
			if (buf[ptr] == '#' || buf[ptr] == '\n') {
				ptr = RawParseUtils.nextLF(buf
				continue;
			}

			if (buf[ptr] == '+') {
				tips.add(ObjectId.fromString(buf
				ptr = RawParseUtils.nextLF(buf
				continue;
			}

			List<String> names = new ArrayList<String>(4);
			while (ptr < buf.length && buf[ptr] == 'P') {
				int end = RawParseUtils.nextLF(buf
				if (buf[end - 1] == '\n')
					end--;
				names.add(RawParseUtils.decode(buf
				ptr = RawParseUtils.nextLF(buf
			}

			if (!tips.isEmpty() && !names.isEmpty()) {
				list.add(new LocalCachedPack(this
				tips = new HashSet<ObjectId>();
			}
		}
		list.trimToSize();
		return new CachedPackList(s
	}

