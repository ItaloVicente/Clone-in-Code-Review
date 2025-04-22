				AbstractTreeIterator leftIter = tw.getTree(leftIndex,
						AbstractTreeIterator.class);
				AbstractTreeIterator rightIter = tw.getTree(rightIndex,
						AbstractTreeIterator.class);
				if (leftIter instanceof WorkingTreeIterator
						&& rightIter == null
						&& tw.getTree(dirCacheIndex,
								DirCacheIterator.class) == null
						&& ((WorkingTreeIterator) leftIter).isEntryIgnored()) {
					continue;
