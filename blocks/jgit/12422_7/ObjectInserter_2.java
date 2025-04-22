	public ObjectReader newReader(Repository db) {
		final ObjectReader reader = db.newObjectReader();
		return new ObjectReader() {
			@Override
			public ObjectReader newReader() {
				return reader.newReader();
			}

			@Override
			public Collection<ObjectId> resolve(AbbreviatedObjectId id)
					throws IOException {
				flush();
				return reader.resolve(id);
			}

			@Override
			public ObjectLoader open(AnyObjectId objectId
					throws MissingObjectException
					IOException {
				flush();
				return reader.open(objectId
			}

			@Override
			public Set<ObjectId> getShallowCommits() throws IOException {
				flush();
				return reader.getShallowCommits();
			}
		};
	}

