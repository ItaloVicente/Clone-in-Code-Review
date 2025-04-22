			try {
				shallowCommitsIds = FileUtils.readWithRetries(shallowFile
					  f -> {
							  FileSnapshot newSnapshot = FileSnapshot.save(shallowFile);
							  HashSet<ObjectId> result = new HashSet<>();
							  try (BufferedReader reader = open(shallowFile)) {
								  String line;
								  while ((line = reader.readLine()) != null) {
									  if (!ObjectId.isId(line)) {
										  throw new IOException(MessageFormat.format(JGitText.get().badShallowLine

									  }
									  result.add(ObjectId.fromString(line));
								  }
							  }
							  shallowFileSnapshot = newSnapshot;
							  return result;
						  });
			} catch (IOException e) {
				throw e;
			} catch (Exception e) {
				throw new IOException(JGitText.get().readShallowFailed
