				AbstractTreeIterator compareVersionIterator = tw.getTree(
						compareTreeIndex, AbstractTreeIterator.class);
				AbstractTreeIterator baseVersionIterator = tw.getTree(
						baseTreeIndex, AbstractTreeIterator.class);
				if (checkIgnored
						&& baseVersionIterator != null
						&& ((WorkingTreeIterator) baseVersionIterator)
								.isEntryIgnored())
					continue;


				if (compareVersionIterator != null
						&& baseVersionIterator != null) {
					boolean equalContent = compareVersionIterator
							.getEntryObjectId().equals(
									baseVersionIterator.getEntryObjectId());
					if (equalContent)
						continue;
				}

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
				}

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
