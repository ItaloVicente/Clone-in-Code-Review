	@Override
	Set<ObjectId> getShallowCommits() throws IOException {
		final File shallow = shallowFile;
		if (!shallow.isFile())
			return Collections.emptySet();

		final BufferedReader reader = new BufferedReader(
				new FileReader(shallow));
		try {
			final Set<ObjectId> ids = new HashSet<ObjectId>();
			String line;
			while ((line = reader.readLine()) != null) {
				final ObjectId id = ObjectId.fromString(line);
				ids.add(id);
			}

			return ids;
		} finally {
			reader.close();
		}
	}

