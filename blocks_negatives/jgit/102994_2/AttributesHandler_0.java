		AttributesNode rootNode = attributesNode(treeWalk,
				rootOf(
						treeWalk.getTree(WorkingTreeIterator.class)),
				rootOf(
						treeWalk.getTree(DirCacheIterator.class)),
				rootOf(treeWalk
						.getTree(CanonicalTreeParser.class)));
