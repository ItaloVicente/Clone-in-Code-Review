					 FileTreeIterator f = tw.getTree(1, FileTreeIterator.class);
						DirCacheEntry entry = new DirCacheEntry(path);
						entry.setLength((int)f.getEntryLength());
						entry.setLastModified(f.getEntryLastModified());
						entry.setFileMode(f.getEntryFileMode());
						entry.setObjectId(ow.writeBlob(file));

						builder.add(entry);
						lastAddedFile = path;
					} else {
						c = tw.getTree(0, DirCacheIterator.class);
						builder.add(c.getDirCacheEntry());
