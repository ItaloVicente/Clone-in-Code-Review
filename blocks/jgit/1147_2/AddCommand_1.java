					if (!(update && tw.getTree(0
						FileTreeIterator f = tw.getTree(1
							DirCacheEntry entry = new DirCacheEntry(path);
							entry.setLength((int)f.getEntryLength());
							entry.setLastModified(f.getEntryLastModified());
							entry.setFileMode(f.getEntryFileMode());
							entry.setObjectId(ow.writeBlob(file));

							builder.add(entry);
							lastAddedFile = path;
						} else if (!update){
							c = tw.getTree(0
							builder.add(c.getDirCacheEntry());
						}
