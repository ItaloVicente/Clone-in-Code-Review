				if (leftIter instanceof WorkingTreeIterator
						&& tw.getTree(dirCacheIndex,
								DirCacheIterator.class) == null
						&& ((WorkingTreeIterator) leftIter).isEntryIgnored()) {
					continue;
				}
