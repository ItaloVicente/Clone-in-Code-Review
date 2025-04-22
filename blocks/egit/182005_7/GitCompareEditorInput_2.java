				if (leftIter instanceof WorkingTreeIterator
						&& rightIter == null
						&& tw.getTree(dirCacheIndex,
								DirCacheIterator.class) == null
						&& ((WorkingTreeIterator) leftIter).isEntryIgnored()) {
					continue;
				}

