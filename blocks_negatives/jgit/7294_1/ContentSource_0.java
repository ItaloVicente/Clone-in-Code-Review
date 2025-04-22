	private static class FileSource extends ContentSource {
		private final File root;

		FileSource(File root) {
			this.root = root;
		}

		@Override
		public long size(String path, ObjectId id) throws IOException {
			return new File(root, path).length();
		}

		@Override
		public ObjectLoader open(String path, ObjectId id) throws IOException {
			final File p = new File(root, path);
			if (!p.isFile())
				throw new FileNotFoundException(path);
			return new ObjectLoader() {
				@Override
				public long getSize() {
					return p.length();
				}

				@Override
				public int getType() {
					return Constants.OBJ_BLOB;
				}

				@Override
				public ObjectStream openStream() throws MissingObjectException,
						IOException {
					final FileInputStream in = new FileInputStream(p);
					final long sz = in.getChannel().size();
					final int type = getType();
					final BufferedInputStream b = new BufferedInputStream(in);
					return new ObjectStream.Filter(type, sz, b);
				}

				@Override
				public boolean isLarge() {
					return true;
				}

				@Override
				public byte[] getCachedBytes() throws LargeObjectException {
					throw new LargeObjectException();
				}
			};
		}
	}

