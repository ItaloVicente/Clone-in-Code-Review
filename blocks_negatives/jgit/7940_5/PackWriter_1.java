		final List<ObjectToPack> list = sortByName();
		final PackIndexWriter iw;
		int indexVersion = config.getIndexVersion();
		if (indexVersion <= 0)
			iw = PackIndexWriter.createOldestPossible(indexStream, list);
		else
			iw = PackIndexWriter.createVersion(indexStream, indexVersion);
		iw.write(list, packcsum);
