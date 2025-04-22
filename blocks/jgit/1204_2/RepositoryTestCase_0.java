			entry.add("length(index/file): "
				+ ((dcIt == null) ? "null" : dcIt.getDirCacheEntry().getLength()
					+ "/"
				+ ((fIt == null) ? "null" : fIt.getEntryLength())));
			entry.add("mode(index/file): "
					+ ((dcIt == null) ? "null" : dcIt.getEntryFileMode()
						+ "/"
					+ ((fIt == null) ? "null" : fIt.getEntryFileMode())));
			if (dcIt != null)
				entry.add("stage: "
					+ dcIt.getDirCacheEntry().getStage());
