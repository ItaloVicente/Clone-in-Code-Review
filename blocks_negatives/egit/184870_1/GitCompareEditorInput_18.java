
				String encoding = null;
				CheckoutMetadata metadata = null;

				GitFileRevision compareRev = null;
				if (compareVersionIterator != null) {
					String entryPath = compareVersionIterator.getEntryPathString();
					encoding = CompareCoreUtils.getResourceEncoding(repository, entryPath);
					if (!useIndex) {
						metadata = new CheckoutMetadata(tw.getEolStreamType(
								TreeWalk.OperationType.CHECKOUT_OP),
								tw.getFilterCommand(
										Constants.ATTR_FILTER_TYPE_SMUDGE));
						compareRev = GitFileRevision.inCommit(repository,
								compareCommit, entryPath,
								tw.getObjectId(compareTreeIndex), metadata);
					} else {
						compareRev = GitFileRevision.inIndex(repository,
								entryPath);
					}
