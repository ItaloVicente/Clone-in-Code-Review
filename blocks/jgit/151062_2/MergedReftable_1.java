	private class FilteringMergedRefCursor extends MergedRefCursor {
		AnyObjectId filterId;
		Ref filteredRef;

		FilteringMergedRefCursor(AnyObjectId id) {
			filterId = id;
			filteredRef = null;
		}

		@Override
		public Ref getRef() {
			return filteredRef;
		}

		@Override
		public boolean next() throws IOException {
			for (;;) {
				boolean ok = super.next();
				if (!ok) {
					return false;
				}

				String name = super.getRef().getName();

				try (RefCursor c = seekRef(name)) {
					if (c.next()) {
						if (filterId.equals(c.getRef().getObjectId())) {
							filteredRef = c.getRef();
							return true;
						}
					}
				}
			}
		}
	}

