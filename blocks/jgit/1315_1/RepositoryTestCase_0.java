	protected void resetIndex(FileTreeIterator fIt) throws FileNotFoundException
			IOException {
				ObjectInserter inserter = db.newObjectInserter();
				DirCacheBuilder builder = db.lockDirCache().builder();
				DirCacheEntry dce;

				while (!fIt.eof()) {
					dce = new DirCacheEntry(fIt.getEntryPathString());
					dce.setFileMode(fIt.getEntryFileMode());
					dce.setLastModified(fIt.getEntryLastModified());
					dce.setLength((int) fIt.getEntryLength());
					FileInputStream in = new FileInputStream(new File(fIt.getDirectory()
							.getEntryPathString()));
					dce.setObjectId(inserter.insert(
							Constants.OBJ_BLOB
							fIt.getEntryLength()
							in));
					in.close();
					builder.add(dce);
					fIt.next(1);
				}
				builder.commit();
			}

