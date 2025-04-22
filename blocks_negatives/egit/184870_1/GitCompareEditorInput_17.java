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
