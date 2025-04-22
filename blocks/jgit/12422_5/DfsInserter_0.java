
	private class Reader extends ObjectReader {
		private ObjectReader delegate;

		@Override
		public ObjectReader newReader() {
			return db.newReader();
		}

		@Override
		public Collection<ObjectId> resolve(AbbreviatedObjectId id)
				throws IOException {
			ObjectReader r = newReader();
			try {
				Collection<ObjectId> fromDb = r.resolve(id);
				Set<ObjectId> ids = new HashSet<ObjectId>(fromDb.size() + 2);
				ids.addAll(fromDb);
				for (PackedObjectInfo obj : objectList) {
					if (id.prefixCompare(obj) == 0)
						ids.add(obj.copy());
				}
				return ids;
			} finally {
				r.release();
			}
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
				throws MissingObjectException
				IOException {
			PackedObjectInfo obj = objectMap.get(objectId);
			if (obj != null)
				return new StreamLoader(obj.getOffset());
			if (delegate == null)
				delegate = newReader();
			return delegate.open(objectId
		}

		@Override
		public Set<ObjectId> getShallowCommits() throws IOException {
			return Collections.emptySet();
		}

		@Override
		public void release() {
			if (delegate != null) {
				delegate.release();
				delegate = null;
			}
		}
	}

	private class StreamLoader extends ObjectLoader {
		private final InputStream in;
		private final int type;
		private final long size;

		private StreamLoader(long position) throws IOException {
			if (packOut == null)
				throw new IllegalStateException(DfsText.get().unexpectedEofInPack);
			in = packOut.out.openInputStream(position);

			int c = in.read();
			if (c < 0)
					throw new IllegalStateException(DfsText.get().unexpectedEofInPack);
			type = (c >> 4) & 7;
			if (type == OBJ_OFS_DELTA || type == OBJ_REF_DELTA)
				throw new IllegalStateException(MessageFormat.format(
						DfsText.get().readBackDelta

			long sz = c & 0x0f;
			int shift = 4;
			while ((c & 0x80) != 0) {
				c = in.read();
				if (c < 0)
					throw new IllegalStateException(DfsText.get().unexpectedEofInPack);
				sz += ((long) (c & 0x7f)) << shift;
				shift += 7;
			}
			size = sz;
		}

		@Override
		public int getType() {
			return type;
		}

		@Override
		public long getSize() {
			return size;
		}

		@Override
		public boolean isLarge() {
			return true;
		}

		@Override
		public byte[] getCachedBytes() throws LargeObjectException {
			throw new LargeObjectException();
		}

		@Override
		public ObjectStream openStream() throws IOException {
			int bufsz = 8192;
			final Inflater inf = InflaterCache.get();
			return new ObjectStream.Filter(type
					new BufferedInputStream(
							new InflaterInputStream(in
							bufsz)) {
				@Override
				public void close() throws IOException {
					InflaterCache.release(inf);
					super.close();
				}
			};
		}
	}
