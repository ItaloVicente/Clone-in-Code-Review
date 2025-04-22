
				String path = tw.getPathString();
				if (path.equals(lastAddedFile)) {
					continue;
				}

					if (c != null
							&& (!update || GITLINK == c.getEntryFileMode())) {
						builder.add(c.getDirCacheEntry());
					}
					continue;
				}

				if (c != null && c.getDirCacheEntry() != null
						&& c.getDirCacheEntry().isAssumeValid()) {
					builder.add(c.getDirCacheEntry());
					continue;
				}

				long sz = f.getEntryLength();
				DirCacheEntry entry = new DirCacheEntry(path);
				FileMode mode = f.getIndexFileMode(c);
				entry.setFileMode(mode);

				if (GITLINK != mode) {
					entry.setLength(sz);
					entry.setLastModified(f.getEntryLastModified());
					long len = f.getEntryContentLength();
					try (InputStream in = f.openEntryStream()) {
						ObjectId id = inserter.insert(OBJ_BLOB
						entry.setObjectId(id);
