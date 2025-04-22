		if (r == null)
			r = parseNew(id
		else
			parseHeaders(r);
		return r;
	}

	private RevObject parseNew(AnyObjectId id
			throws CorruptObjectException
		RevObject r;
		int type = ldr.getType();
		switch (type) {
		case Constants.OBJ_COMMIT: {
			final RevCommit c = createCommit(id);
			c.parseCanonical(this
			r = c;
			break;
		}
		case Constants.OBJ_TREE: {
			r = new RevTree(id);
			r.flags |= PARSED;
			break;
		}
		case Constants.OBJ_BLOB: {
			r = new RevBlob(id);
			r.flags |= PARSED;
			break;
		}
		case Constants.OBJ_TAG: {
			final RevTag t = new RevTag(id);
			t.parseCanonical(this
			r = t;
			break;
		}
		default:
			throw new IllegalArgumentException(MessageFormat.format(JGitText
					.get().badObjectType
		}
		objects.add(r);
		return r;
	}

	public <T extends ObjectId> AsyncRevObjectQueue parseAny(
			Iterable<T> objectIds
		List<T> need = new ArrayList<T>();
		List<RevObject> have = new ArrayList<RevObject>();
		for (T id : objectIds) {
			RevObject r = objects.get(id);
			if (r != null && (r.flags & PARSED) != 0)
				have.add(r);
			else
				need.add(id);
		}

		final Iterator<RevObject> objItr = have.iterator();
		if (need.isEmpty()) {
			return new AsyncRevObjectQueue() {
				public RevObject next() {
					return objItr.hasNext() ? objItr.next() : null;
				}

				public boolean cancel(boolean mayInterruptIfRunning) {
					return true;
				}

				public void release() {
				}
			};
		}

		final AsyncObjectLoaderQueue<T> lItr = reader.open(need
		return new AsyncRevObjectQueue() {
			public RevObject next() throws MissingObjectException
					IncorrectObjectTypeException
				if (objItr.hasNext())
					return objItr.next();
				if (!lItr.next())
					return null;

				ObjectId id = lItr.getObjectId();
				ObjectLoader ldr = lItr.open();
				RevObject r = objects.get(id);
				if (r == null)
					r = parseNew(id
				else if (r instanceof RevCommit) {
					byte[] raw = ldr.getCachedBytes();
					((RevCommit) r).parseCanonical(RevWalk.this
				} else if (r instanceof RevTag) {
					byte[] raw = ldr.getCachedBytes();
					((RevTag) r).parseCanonical(RevWalk.this
				} else
					r.flags |= PARSED;
				return r;
