	public int getIndexVersion() {
		int indexVersion = config.getIndexVersion();
		if (indexVersion <= 0) {
			for (BlockList<ObjectToPack> objs : objectsLists)
				indexVersion = Math.max(indexVersion
						PackIndexWriter.oldestPossibleFormat(objs));
		} else if (writeBitmaps != null && indexVersion == 2) {
			indexVersion = 0xE003;
		}
		return indexVersion;
	}

