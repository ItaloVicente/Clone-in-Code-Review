			DirCacheEntry e = new DirCacheEntry(path, stage);
			e.setFileMode(p.getEntryFileMode());
			e.setObjectId(p.getEntryObjectId());
			e.setLastModified(lastMod);
			e.setLength(len);
			builder.add(e);
			return e;
