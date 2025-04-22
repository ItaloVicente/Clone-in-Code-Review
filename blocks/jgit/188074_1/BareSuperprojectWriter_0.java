
	private static class IndexWriter {
		private DirCache index;

		private DirCacheBuilder builder;

		private ObjectInserter inserter;

		IndexWriter(ObjectInserter inserter) {
			this.index = DirCache.newInCore();
			this.builder = index.builder();
			this.inserter = inserter;

		}

		void addRegularFile(String path
			addFileWithMode(path
					FileMode.REGULAR_FILE);
		}

		void addSymlink(String path
			addFileWithMode(path
		}

		void addFileWithMode(String path
				throws IOException {
			DirCacheEntry dce = new DirCacheEntry(path);

			ObjectId oid = inserter.insert(Constants.OBJ_BLOB
					contents);
			dce.setObjectId(oid);
			dce.setFileMode(mode);
			builder.add(dce);
		}

		void addGitlink(String path
			DirCacheEntry dce = new DirCacheEntry(path);
			dce.setObjectId(objectId);
			dce.setFileMode(FileMode.GITLINK);
			builder.add(dce);
		}

		ObjectId create() throws UnmergedPathException
			builder.finish();
			return index.writeTree(inserter);
		}
	}
