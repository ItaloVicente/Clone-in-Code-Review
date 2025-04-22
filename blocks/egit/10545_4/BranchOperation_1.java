						try {
							FileUtils.delete(projectDir,
									FileUtils.EMPTY_DIRECTORIES_ONLY
											| FileUtils.RECURSIVE
											| FileUtils.IGNORE_ERRORS);
						} catch (IOException e) {
						}
