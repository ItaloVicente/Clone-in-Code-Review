				RevCommit bc = getBaseCommit(currentBase
						callDepth + 1);
				AbstractTreeIterator bcTree = (bc == null) ? new EmptyTreeIterator()
						: openTree(bc.getTree());
				if (mergeTrees(bcTree
						nextBase.getTree()
