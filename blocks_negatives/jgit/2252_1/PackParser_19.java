	private void writeIdx() throws IOException {
		Arrays.sort(entries, 0, entryCount);
		List<PackedObjectInfo> list = Arrays.asList(entries);
		if (entryCount < entries.length)
			list = list.subList(0, entryCount);

		final FileOutputStream os = new FileOutputStream(dstIdx);
		try {
			final PackIndexWriter iw;
			if (outputVersion <= 0)
				iw = PackIndexWriter.createOldestPossible(os, list);
			else
				iw = PackIndexWriter.createVersion(os, outputVersion);
			iw.write(list, packcsum);
			os.getChannel().force(true);
		} finally {
			os.close();
		}
	}

