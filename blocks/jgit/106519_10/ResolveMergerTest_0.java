	@Theory
	public void checkContentMergeLargeBinaries(MergeStrategy strategy) throws Exception {
		Git git = Git.wrap(db);
		final int LINELEN = 72;

		byte[] binary = new byte[LINELEN * 2000];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte)((i % LINELEN) == 0 ? '\n' : 'x');
		}
		binary[50] = '\0';

		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit first = git.commit().setMessage("added file").call();

        	int idx = LINELEN * 1200 + 1;
		byte save = binary[idx];
		binary[idx] = '@';
		writeTrashFile("file"

		binary[idx] = save;
		git.add().addFilepattern("file").call();
		RevCommit masterCommit = git.commit().setAll(true)
			.setMessage("modified file l 1200").call();

		git.checkout().setCreateBranch(true).setStartPoint(first).setName("side").call();
		binary[LINELEN * 1500 + 1] = '!';
		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		RevCommit sideCommit = git.commit().setAll(true)
			.setMessage("modified file l 1500").call();

		try (ObjectInserter ins = db.newObjectInserter()) {
			ObjectInserter forbidInserter = new ObjectInserter.Filter() {
				@Override
				protected ObjectInserter delegate() {
					return ins;
				}
				@Override
				public ObjectReader newReader() {
					return new BigReadForbiddenReader(super.newReader()
				}
			};

			ResolveMerger merger =
				(ResolveMerger) strategy.newMerger(forbidInserter
			boolean noProblems = merger.merge(masterCommit
			assertFalse(noProblems);
		}
	}

	abstract class ObjectLoaderFilter extends ObjectLoader {
		protected abstract ObjectLoader delegate();

		@Override
		public int getType() {
			return delegate().getType();
		}

		@Override
		public long getSize() {
			return delegate().getSize();
		}

		@Override
		public boolean isLarge() {
			return delegate().isLarge();
		}

		@Override
		public byte[] getCachedBytes() {
			return delegate().getCachedBytes();
		}

		@Override
		public ObjectStream openStream() throws IOException {
			return delegate().openStream();
		}
	}

	class BigReadForbiddenStream extends ObjectStream.Filter {
		int limit;
		BigReadForbiddenStream(ObjectStream orig
			super(orig.getType()
			this.limit = limit;
		}

		@Override
		public long skip(long n) throws IOException {
			limit -= n;
			if (limit < 0) {
				throw new IllegalStateException();
			}

			return super.skip(n);
		}

		@Override
		public int read() throws IOException {
			int r = super.read();
			limit --;
			if (limit < 0) {
				throw new IllegalStateException();
			}
			return r;
		}

		@Override
		public int read(byte[] b
			int n = super.read(b
			limit -= n;
			if (limit < 0) {
				throw new IllegalStateException();
			}
			return n;
		}
	}

	class BigReadForbiddenReader extends ObjectReader.Filter {
		ObjectReader delegate;
		int limit;

		@Override
		protected ObjectReader delegate() {
			return delegate;
		}

		BigReadForbiddenReader(ObjectReader delegate
			this.delegate = delegate;
			this.limit = limit;
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
			ObjectLoader orig = super.open(objectId
			return new ObjectLoaderFilter() {
				@Override
				protected ObjectLoader delegate() {
					return orig;
				}

				@Override
				public ObjectStream openStream() throws IOException {
					ObjectStream os = orig.openStream();
					return new BigReadForbiddenStream(os
				}
			};
		}
	}

