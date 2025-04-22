
		if (shallowFileSnapshot == null
				|| shallowFileSnapshot.isModified(shallowFile)) {
			shallowCommitsIds = new HashSet<>();

			final BufferedReader reader = open(shallowFile);
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					try {
						shallowCommitsIds.add(ObjectId.fromString(line));
					} catch (IllegalArgumentException ex) {
						throw new IOException(MessageFormat
								.format(JGitText.get().badShallowLine, line));
					}
				}
			} finally {
				reader.close();
			}

			shallowFileSnapshot = FileSnapshot.save(shallowFile);
