	@Override
	Set<ObjectId> getShallowCommits() throws IOException {
		if (shallowFile == null || !shallowFile.isFile())
			return Collections.emptySet();

		if (shallowFileSnapshot == null
				|| shallowFileSnapshot.isModified(shallowFile)) {
			shallowCommitsIds = new HashSet<ObjectId>();

			final BufferedReader reader = open(shallowFile);
			try {
				String line;
				while ((line = reader.readLine()) != null)
					shallowCommitsIds.add(ObjectId.fromString(line));
			} finally {
				reader.close();
			}

			shallowFileSnapshot = FileSnapshot.save(shallowFile);
		}

		return shallowCommitsIds;
	}

