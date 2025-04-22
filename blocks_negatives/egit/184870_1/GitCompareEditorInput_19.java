				GitFileRevision baseRev = null;
				if (baseVersionIterator != null) {
					String entryPath = baseVersionIterator.getEntryPathString();
					if (encoding == null) {
						encoding = CompareCoreUtils.getResourceEncoding(repository, entryPath);
					}
					if (metadata == null) {
						metadata = new CheckoutMetadata(
								tw.getEolStreamType(
										TreeWalk.OperationType.CHECKOUT_OP),
								tw.getFilterCommand(
										Constants.ATTR_FILTER_TYPE_SMUDGE));
					}
					baseRev = GitFileRevision.inCommit(repository, baseCommit,
							entryPath, tw.getObjectId(baseTreeIndex), metadata);
