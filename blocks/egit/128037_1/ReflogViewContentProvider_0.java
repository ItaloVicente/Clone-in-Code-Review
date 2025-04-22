						.map(entry -> {
							String commitMessage = null;
							try {
								commitMessage = walk
										.parseCommit(entry.getNewId())
										.getShortMessage();
							} catch (IOException e) {
							}
							return new ReflogItem(ReflogInput.this, entry,
									commitMessage);
						})
