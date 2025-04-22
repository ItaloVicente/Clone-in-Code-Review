	public <T extends ObjectId> AsyncObjectLoaderQueue<T> open(
			Iterable<T> objectIds
		final Iterator<T> idItr = objectIds.iterator();
		return new AsyncObjectLoaderQueue<T>() {
			private T cur;

			public boolean next() throws MissingObjectException
				if (idItr.hasNext()) {
					cur = idItr.next();
					return true;
				} else {
					return false;
				}
			}

			public T getCurrent() {
				return cur;
			}

			public ObjectId getObjectId() {
				return cur;
			}

			public ObjectLoader open() throws IOException {
				return ObjectReader.this.open(cur
			}

			public boolean cancel(boolean mayInterruptIfRunning) {
				return true;
			}

			public void release() {
			}
		};
	}

