			DirCacheEntry dce = new DirCacheEntry(path
			dce.setFileMode(p.getEntryFileMode());
			if (lastMod != null) {
				dce.setLastModified(lastMod);
			}
			dce.setLength((int) len);
			dce.setObjectId(p.getEntryObjectId());
			workTreeUpdater.insertToIndex(dce);
			return dce;
