						try {
							FileUtils.delete(projectDir,
									FileUtils.EMPTY_DIRECTORY
											| FileUtils.RECURSIVE
											| FileUtils.IGNORE_ERRORS);
						} catch (IOException e) {
						}
