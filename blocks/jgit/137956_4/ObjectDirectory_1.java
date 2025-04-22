						if (searchPacksAgain(pList
							if (p.incrementTransientErrorCount() < 6) {
								continue SEARCH;
							} else {
								LOG.error(MessageFormat.format(JGitText
										.get().assumePersistentPackProblem
										Integer.valueOf(5)
										p.getPackFile().getAbsolutePath()));
								handlePackError(e
							}
						} else {
							handlePackError(e
						}
