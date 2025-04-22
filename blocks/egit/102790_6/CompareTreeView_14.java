						metadata = new CheckoutMetadata(
								tw.getEolStreamType(
										TreeWalk.OperationType.CHECKOUT_OP),
								tw.getFilterCommand(
										Constants.ATTR_FILTER_TYPE_SMUDGE));
						left = GitFileRevision.inCommit(repository, baseCommit,
								repoRelativePath, tw.getObjectId(baseTreeIndex),
								metadata);
