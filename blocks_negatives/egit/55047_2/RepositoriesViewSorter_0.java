		if (e1 instanceof StashedCommitNode && e2 instanceof StashedCommitNode) {
			return ((StashedCommitNode)e1).getIndex() - ((StashedCommitNode)e1).getIndex();
		} else if (e1 instanceof TagNode && e2 instanceof TagNode) {
			String name1 = ((TagNode) e1).getObject().getName();
			String name2 = ((TagNode) e2).getObject().getName();
			return CommonUtils.STRING_ASCENDING_COMPARATOR
					.compare(name1, name2);
		} else
