				onlyProcessed[pos] = true;
			} else {
				if (hTree != null) {
					final DirCacheEntry dcEntry = new DirCacheEntry(path);
					dcEntry.setObjectId(hTree.getEntryObjectId());
					dcEntry.setFileMode(hTree.getEntryFileMode());

					tempBuilder.add(dcEntry);
