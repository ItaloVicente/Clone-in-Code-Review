			if (dirCacheIterator != null) {
				if (workingTreeIterator == null) {
					missing.add(dirCacheIterator.getEntryPathString());
					changesExist = true;
				} else {
					if (!dirCacheIterator.idEqual(workingTreeIterator)) {
						modified.add(dirCacheIterator.getEntryPathString());
						changesExist = true;
					}
				}
			}
		}
		return changesExist;
