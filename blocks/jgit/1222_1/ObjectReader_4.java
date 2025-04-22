	public <T extends ObjectId> AsyncObjectSizeQueue<T> getObjectSize(
			Iterable<T> objectIds
		final Iterator<T> idItr = objectIds.iterator();
		return new AsyncObjectSizeQueue<T>() {
			private T cur;

			private long sz;

			public boolean next() throws MissingObjectException
				if (idItr.hasNext()) {
					cur = idItr.next();
					sz = getObjectSize(cur
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

			public long getSize() {
				return sz;
			}

			public boolean cancel(boolean mayInterruptIfRunning) {
				return true;
			}

			public void release() {
			}
		};
	}

