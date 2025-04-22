		sinceLastIndex++;
		if (++inQueue > REBUILD_INDEX_COUNT
				&& sinceLastIndex > REBUILD_INDEX_COUNT) {
			sinceLastIndex = 0;
			buildIndex();
		}

