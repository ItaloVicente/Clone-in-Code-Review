			try {
				shallowCommitsIds = FileUtils.readWithRetries(shallowFile
						f -> {
							FileSnapshot newSnapshot = FileSnapshot.save(f);
							HashSet<ObjectId> result = new HashSet<>();
							try (BufferedReader reader = open(f)) {
								String line;
								while ((line = reader.readLine()) != null) {
									if (!ObjectId.isId(line)) {
										throw new IOException(
												MessageFormat.format(JGitText
														.get().badShallowLine
														f.getAbsolutePath()
														line));

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
				throw new IOException(
						MessageFormat.format(JGitText.get().readShallowFailed
								shallowFile.getAbsolutePath())
						e);
