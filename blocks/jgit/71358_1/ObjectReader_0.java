
	public static abstract class Filter extends ObjectReader {
		protected abstract ObjectReader delegate();

		@Override
		public ObjectReader newReader() {
			return delegate().newReader();
		}

		@Override
		public AbbreviatedObjectId abbreviate(AnyObjectId objectId)
				throws IOException {
			return delegate().abbreviate(objectId);
		}

		@Override
		public AbbreviatedObjectId abbreviate(AnyObjectId objectId
				throws IOException {
			return delegate().abbreviate(objectId
		}

		@Override
		public Collection<ObjectId> resolve(AbbreviatedObjectId id)
				throws IOException {
			return delegate().resolve(id);
		}

		@Override
		public boolean has(AnyObjectId objectId) throws IOException {
			return delegate().has(objectId);
		}

		@Override
		public boolean has(AnyObjectId objectId
			return delegate().has(objectId
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId)
				throws MissingObjectException
			return delegate().open(objectId);
		}

		@Override
		public ObjectLoader open(AnyObjectId objectId
				throws MissingObjectException
				IOException {
			return delegate().open(objectId
		}

		@Override
		public Set<ObjectId> getShallowCommits() throws IOException {
			return delegate().getShallowCommits();
		}

		@Override
		public <T extends ObjectId> AsyncObjectLoaderQueue<T> open(
				Iterable<T> objectIds
			return delegate().open(objectIds
		}

		@Override
		public long getObjectSize(AnyObjectId objectId
				throws MissingObjectException
				IOException {
			return delegate().getObjectSize(objectId
		}

		@Override
		public <T extends ObjectId> AsyncObjectSizeQueue<T> getObjectSize(
				Iterable<T> objectIds
			return delegate().getObjectSize(objectIds
		}

		@Override
		public void setAvoidUnreachableObjects(boolean avoid) {
			delegate().setAvoidUnreachableObjects(avoid);
		}

		@Override
		public BitmapIndex getBitmapIndex() throws IOException {
			return delegate().getBitmapIndex();
		}

		@Override
		public void close() {
			delegate().close();
		}
	}
