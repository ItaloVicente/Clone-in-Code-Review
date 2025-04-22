						metadata = new CheckoutMetadata(tw.getEolStreamType(),
								tw.getFilterCommand(
										Constants.ATTR_FILTER_TYPE_SMUDGE));
						left = GitFileRevision.inCommit(repository, baseCommit,
								repoRelativePath, tw.getObjectId(baseTreeIndex),
								metadata);
